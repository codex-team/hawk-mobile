package so.codex.hawk.di.modules

import dagger.Module
import dagger.Provides
import so.codex.hawk.domain.FetchProjectsInteractor
import so.codex.hawk.domain.providers.ProjectProvider
import so.codex.hawk.domain.providers.WorkspaceProvider
import javax.inject.Singleton

/**
 * Dependencies that are required for working with projects are declared in this module
 */
@Module
class ProjectModule {
    /**
     * Provide project provider
     * @param workspaceProvider for getting all workspace that have list of projects
     * @return [ProjectProvider]
     */
    @Singleton
    @Provides
    fun projectProvider(workspaceProvider: WorkspaceProvider): ProjectProvider {
        return ProjectProvider(workspaceProvider)
    }

    /**
     * Provide fetch project interactor
     * @param projectProvider for getting only information of projects
     * @return [FetchProjectsInteractor]
     */
    @Singleton
    @Provides
    fun fetchProjectInteractor(projectProvider: ProjectProvider): FetchProjectsInteractor {
        return FetchProjectsInteractor(projectProvider)
    }
}