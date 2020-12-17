package so.codex.hawk.logging.http

import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.internal.http.promisesBody
import okio.Buffer
import okio.GzipSource
import timber.log.Timber

/**
 * The class is the interceptor of the api request.
 * This interceptor logs HTTP requests.
 */
class HttpFileLoggingInterceptor : Interceptor {
    /**
     * @property level Current request logging level.
     * @see Level
     */
    @Volatile
    var level = Level.NONE

    /**
     * @property headersToRedact contains a set of headers for editing.
     */
    @Volatile
    private var headersToRedact = emptySet<String>()

    /**
     * The class is an enumeration of the levels of possible logging.
     */
    enum class Level {
        /**
         * @property NONE No logs.
         */
        NONE,

        /**
         * @property BASIC Logs request and response lines.
         *
         * Example:
         * ```
         * --> POST /greeting http/1.1 (3-byte body)
         *
         * <-- 200 OK (22ms, 6-byte body)
         * ```
         */
        BASIC,

        /**
         * @property HEADERS Logs request and response lines and their respective headers.
         *
         * Example:
         * ```
         * --> POST /greeting http/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         * --> END POST
         *
         * <-- 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         * <-- END HTTP
         * ```
         */
        HEADERS,

        /**
         * @property BODY Logs request and response lines and their respective
         *                headers and bodies (if present).
         *
         *  Example:
         * ```
         * --> POST /greeting http/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         *
         * Hi?
         * --> END POST
         *
         * <-- 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         *
         * Hello!
         * <-- END HTTP
         * ```
         */
        BODY
    }

    /**
     * The method that will be called upon request to the api
     * when the interceptor's turn in the chain comes.
     *
     * @param chain Chain of requests. Provided by the client using the given interceptor.
     *
     * @return An HTTP response.
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        val level = this.level
        val request = chain.request()
        if (level == Level.NONE) {
            return chain.proceed(request)
        }
        val logBody = level == Level.BODY
        val logHeaders = logBody || level == Level.HEADERS
        val requestBody = request.body
        val connection = chain.connection()

        var requestStartMessage =
            "\n--> ${request.method} ${request.url}" +
                "${if (connection != null) " " + connection.protocol() else ""}\n"
        if (!logHeaders && requestBody != null) {
            requestStartMessage += " (${requestBody.contentLength()}-byte body)\n"
        }
        Timber.i(requestStartMessage)
        if (logHeaders) {
            val headers = request.headers
            if (requestBody != null) {
                requestBody.contentType()?.let {
                    if (headers["Content-Type"] == null) {
                        Timber.i("Content-Type: $it\n")
                    }
                }
                if (requestBody.contentLength() != -1L) {
                    if (headers["Content-Length"] == null) {
                        Timber.i("Content-Length: ${requestBody.contentLength()}\n")
                    }
                }
            }
            for (i in 0 until headers.size) {
                logHeader(headers, i)
            }
            if (!logBody || requestBody == null) {
                Timber.i("--> END ${request.method}\n")
            } else if (bodyHasUnknownEncoding(request.headers)) {
                Timber.i("--> END ${request.method} (encoded body omitted)\n")
            } else if (requestBody.isDuplex()) {
                Timber.i("--> END ${request.method} (duplex request body omitted)\n")
            } else if (requestBody.isOneShot()) {
                Timber.i("--> END ${request.method} (one-shot body omitted)\n")
            } else {
                val buffer = Buffer()
                requestBody.writeTo(buffer)
                val contentType = requestBody.contentType()
                val charset: Charset =
                    contentType?.charset(StandardCharsets.UTF_8) ?: StandardCharsets.UTF_8
                if (buffer.isProbablyUtf8()) {
                    Timber.i(buffer.readString(charset))
                    Timber.i(
                        "--> END ${request.method} " +
                            "(${requestBody.contentLength()}-byte body)\n"
                    )
                } else {
                    Timber.i(
                        "--> END ${request.method} " +
                            "(binary ${requestBody.contentLength()}-byte body omitted)\n"
                    )
                }
            }
        }
        val startNs = System.nanoTime()
        val response: Response
        try {
            response = chain.proceed(request)
        } catch (e: Exception) {
            Timber.e("\n<-- HTTP FAILED: $e\n")
            throw e
        }
        val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)
        val responseBody = response.body!!
        val contentLength = responseBody.contentLength()
        val bodySize = if (contentLength != -1L) "$contentLength-byte" else "unknown-length\n"
        val message = response.message
        Timber.i(
            "<-- ${response.code}${if (message.isEmpty()) "" else " $message"} " +
                "${response.request.url} (${tookMs}ms" +
                "${if (!logHeaders) ", $bodySize body" else ""})\n"
        )
        if (logHeaders) {
            val headers = response.headers
            for (i in 0 until headers.size) {
                logHeader(headers, i)
            }
            if (!logBody || !response.promisesBody()) {
                Timber.i("<-- END HTTP")
            } else if (bodyHasUnknownEncoding(response.headers)) {
                Timber.i("<-- END HTTP (encoded body omitted)")
            } else {
                val source = responseBody.source()
                source.request(Long.MAX_VALUE) // Buffer the entire body.
                var buffer = source.buffer
                var gzippedLength: Long? = null
                if ("gzip".equals(headers["Content-Encoding"], ignoreCase = true)) {
                    gzippedLength = buffer.size
                    GzipSource(buffer.clone()).use { gzippedResponseBody ->
                        buffer = Buffer()
                        buffer.writeAll(gzippedResponseBody)
                    }
                }
                val contentType = responseBody.contentType()
                val charset: Charset =
                    contentType?.charset(StandardCharsets.UTF_8) ?: StandardCharsets.UTF_8
                if (!buffer.isProbablyUtf8()) {
                    Timber.i("<-- END HTTP (binary ${buffer.size}-byte body omitted)")
                    return response
                }
                if (contentLength != 0L) {
                    Timber.i(buffer.clone().readString(charset))
                }
                if (gzippedLength != null) {
                    Timber.i(
                        "<-- END HTTP (${buffer.size}-byte, "
                            .plus("$gzippedLength-gzipped-byte body)")
                    )
                } else {
                    Timber.i("<-- END HTTP (${buffer.size}-byte body)")
                }
            }
        }
        return response
    }

    /**
     *  Method for adding request headers to the log.
     *
     *  @param headers class containing all request headers.
     *
     *  @param i the index of the header to be logged in this call.
     */
    private fun logHeader(headers: Headers, i: Int) {
        val value = if (headers.name(i) in headersToRedact) "  " else headers.value(i)
        Timber.i("${headers.name(i)}: $value")
    }

    /**
     * Method for checking the encoding of the request body.
     *
     * @param headers class containing all request headers.
     *
     * @return true if the encoding is known.
     */
    private fun bodyHasUnknownEncoding(headers: Headers): Boolean {
        val contentEncoding = headers["Content-Encoding"] ?: return false
        return !contentEncoding.equals("identity", ignoreCase = true) &&
            !contentEncoding.equals("gzip", ignoreCase = true)
    }
}
