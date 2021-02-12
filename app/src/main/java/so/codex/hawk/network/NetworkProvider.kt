package so.codex.hawk.network

import com.apollographql.apollo.ApolloClient
import so.codex.hawk.AppData

/**
 * Class (singleton) that provides an [ApolloClient] instance configured for Api requests.
 */
class NetworkProvider(private val client: ApolloClient) {
    /**
     * Method for getting ApolloClient instance for making requests to Api.
     *
     * @return [ApolloClient] configured for Api requests [AppData.API_URL].
     */
    fun getApolloClient(): ApolloClient {
        return client
    }
}
