package so.codex.hawk.di.modules

import com.apollographql.apollo.ApolloClient
import dagger.Module
import dagger.Provides
import so.codex.hawk.SessionKeeper
import so.codex.hawk.domain.login.LoginInteractor
import so.codex.hawk.domain.login.LoginInteractorImpl
import javax.inject.Singleton

/**
 * In this module should be declared dependencies that need only on Login Screen
 */
@Module
class LoginModule {

    /**
     * Provide login interactor
     * @param client for sending login request
     * @param sessionKeeper for saving session after successful login
     * @return [LoginInteractor]
     */
    @Singleton
    @Provides
    fun loginInteractor(client: ApolloClient, sessionKeeper: SessionKeeper): LoginInteractor {
        return LoginInteractorImpl(client, sessionKeeper)
    }
}