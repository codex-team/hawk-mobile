package so.codex.hawk.network

import okhttp3.Interceptor
import okhttp3.Response
import so.codex.hawk.network.LoggingInterceptor.Companion.instance

/**
 * The class is the interceptor of the api request.
 * An interceptor is required to insert a access token into a request.
 */
class TokenInterceptor private constructor() : Interceptor {
    companion object {
        /**
         * @property instance a field that holds a single instance of [TokenInterceptor].
         *                    The field is lazy initialized  on the first call.
         */
        val instance by lazy {
            TokenInterceptor()
        }

        /**
         * @property HEADER_NAME A constant containing the header to insert into the request.
         */
        private const val HEADER_NAME = "Authorization"
    }

    /**
     * @property token the field contains an access token that
     *                 will be inserted into every api request.
     */
    var token: String = ""


    /**
     * The method that will be called upon request to the api
     * when the interceptor's turn in the chain comes.
     *
     * @param chain Chain of requests. Provided by the client using the given interceptor.
     *
     * @return An HTTP response.
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        val origin = chain.request()
        val localToken = token
        return if (localToken.isNotEmpty()) {
            val request = origin.newBuilder()
                .method(origin.method(), origin.body())
                .addHeader(HEADER_NAME, "Bearer $localToken")
                .build()
            chain.proceed(
                request
            )
        } else {
            chain.proceed(chain.request())
        }
    }
}