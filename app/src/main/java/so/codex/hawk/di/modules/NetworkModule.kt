package so.codex.hawk.di.modules

import com.apollographql.apollo.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import so.codex.hawk.AppData
import so.codex.hawk.SessionKeeper
import so.codex.hawk.domain.refresh.RefreshTokenInteractor
import so.codex.hawk.domain.refresh.RefreshTokenInteractorImpl
import so.codex.hawk.logging.http.HttpFileLoggingInterceptor
import so.codex.hawk.network.TokenInterceptor
import javax.inject.Singleton

/**
 * The main dependencies that are necessary for working with the network are defined in this module
 */
@Module
class NetworkModule {

    /**
     * Logging interceptor
     * @return [Interceptor]
     */
    @Provides
    @IntoSet
    fun loggingInterceptor(): Interceptor {
        val logging = HttpFileLoggingInterceptor()
        logging.level = HttpFileLoggingInterceptor.Level.BODY
        return logging
    }

    /**
     * Token interceptor
     * @param sessionKeeper for getting current session
     * @return [Interceptor]
     */
    @Provides
    @IntoSet
    fun tokenInterceptor(sessionKeeper: SessionKeeper): Interceptor {
        return TokenInterceptor(sessionKeeper)
    }

    /**
     * Configuration of http client
     * @param interceptors set of interceptor which will be processed when the request is received
     *        and sent
     * @return [OkHttpClient]
     */
    @Singleton
    @Provides
    fun httpClient(interceptors: Set<@JvmSuppressWildcards Interceptor>): OkHttpClient {
        val client = OkHttpClient
            .Builder()
        interceptors.forEach(client::addInterceptor)
        return client.build()
    }

    /**
     * Provide apollo client
     * @param okHttp configured http client
     * @return [ApolloClient]
     */
    @Singleton
    @Provides
    fun apolloClient(okHttp: OkHttpClient): ApolloClient {
        return ApolloClient.builder()
            .serverUrl(AppData.API_URL)
            .okHttpClient(okHttp)
            .build()
    }

    /**
     * Provide refresh interactor
     * @param apolloClient for update current session
     * @param sessionKeeper for getting refresh token
     * @return [RefreshTokenInteractor]
     */
    @Singleton
    @Provides
    fun refreshInteractor(
        apolloClient: ApolloClient,
        sessionKeeper: SessionKeeper
    ): RefreshTokenInteractor {
        return RefreshTokenInteractorImpl(apolloClient, sessionKeeper)
    }
}