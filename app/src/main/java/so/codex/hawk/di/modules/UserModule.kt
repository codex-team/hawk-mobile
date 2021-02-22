package so.codex.hawk.di.modules

import com.apollographql.apollo.ApolloClient
import dagger.Module
import dagger.Provides
import so.codex.hawk.domain.main.UserDataInteractor
import so.codex.hawk.domain.main.UserDataInteractorImpl
import javax.inject.Singleton

@Module
class UserModule {

    @Singleton
    @Provides
    fun userDataInteractor(client: ApolloClient): UserDataInteractor {
        return UserDataInteractorImpl(client)
    }
}