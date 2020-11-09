package so.codex.hawk.network

import android.util.Log
import com.apollographql.apollo.ApolloClient
import okhttp3.Interceptor
import okhttp3.Response
import so.codex.hawk.AppData

/**
 * The class is the interceptor of the api request. Required to log all requests.
 */
class LoggingInterceptor private constructor() : Interceptor {
    companion object {
        /**
         * @property instance a field that holds a single instance of [LoggingInterceptor].
         *                    The field is lazy initialized  on the first call.
         */
        val instance by lazy {
            LoggingInterceptor()
        }
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
        Log.d(AppData.LOG_KEY_LOGGING_REQUESTS, origin.toString())
        return chain.proceed(origin)
    }
}