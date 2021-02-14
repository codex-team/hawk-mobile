package so.codex.hawk.di.modules

import com.apollographql.apollo.ApolloClient
import dagger.Module
import dagger.Provides
import so.codex.hawk.domain.FetchWorkspacesInteractor
import so.codex.hawk.domain.providers.WorkspaceProvider
import javax.inject.Singleton

/**
 * This module declares the dependencies that are required to work with workspaces
 */
@Module
class WorkspaceModule {

    /**
     * Provide workspace provider
     * @param client to send a request for information about workspaces
     * @return [WorkspaceProvider]
     */
    @Singleton
    @Provides
    fun workspaceProvider(client: ApolloClient): WorkspaceProvider {
        return WorkspaceProvider(client)
    }

    /**
     * Provide fetch interactor
     * @param provider getting common information about workspace
     * @return [FetchWorkspacesInteractor]
     */
    @Provides
    fun fetchInteractor(provider: WorkspaceProvider): FetchWorkspacesInteractor {
        return FetchWorkspacesInteractor(provider)
    }
}
