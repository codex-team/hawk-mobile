package so.codex.hawk.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import so.codex.hawk.AppData

class LoggingInterceptor: Interceptor {
    companion object {
        val instance by lazy {
            LoggingInterceptor()
        }
    }
    override fun intercept(chain: Interceptor.Chain): Response {
        val origin = chain.request()
        Log.d(AppData.LOG_KEY_LOGGING_REQUESTS,origin.toString())
        return chain.proceed(origin)
    }
}