package so.codex.hawk.network

import com.apollographql.apollo.ApolloClient
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import so.codex.hawk.AppData

/**
 * Class (singleton) that provides an [ApolloClient] instance configured for Api requests.
 */
object NetworkProvider {
    /**
     * @property instance a field that holds a single instance of [ApolloClient].
     *                    The field is lazy initialized  on the first call.
     */
    private val instance by lazy {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
        val okHttp = OkHttpClient
            .Builder()
            .addInterceptor(TokenInterceptor.instance)
            .addInterceptor(logging)
            .build()
        ApolloClient.builder()
            .serverUrl(AppData.API_URL)
            .okHttpClient(okHttp)
            .build()
    }

    /**
     * Method for getting ApolloClient instance for making requests to Api.
     *
     * @return [ApolloClient] configured for Api requests [AppData.API_URL].
     */
    fun getApolloClient(): ApolloClient {
        return instance
    }
}
