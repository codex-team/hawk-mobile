package so.codex.hawk.network

import com.apollographql.apollo.ApolloClient
import okhttp3.OkHttpClient
import so.codex.hawk.AppData

object NetworkProvider {
    private val instance by lazy {
        val okHttp = OkHttpClient
            .Builder()
            .addInterceptor(TokenInterceptor.instance)
            .addInterceptor(LoggingInterceptor.instance)
            .build()
        ApolloClient.builder()
            .serverUrl(AppData.API_URL)
            .okHttpClient(okHttp)
            .build()
    }

    fun getApolloClient(): ApolloClient {
        return instance
    }

}