package so.codex.hawk.network

import okhttp3.Interceptor
import okhttp3.Response
import so.codex.hawk.SessionKeeper

/**
 * The class is the interceptor of the api request.
 * An interceptor is required to insert a access token into a request.
 */
class TokenInterceptor(private val sessionKeeper: SessionKeeper) : Interceptor {
    companion object {
        /**
         * @property HEADER_NAME A constant containing the header to insert into the request.
         */
        private const val HEADER_NAME = "Authorization"
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
        val origin = chain.request()
        val localToken = sessionKeeper.session.token.accessToken
        return if (localToken.isNotEmpty()) {
            val request = origin.newBuilder()
                .method(origin.method, origin.body)
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
