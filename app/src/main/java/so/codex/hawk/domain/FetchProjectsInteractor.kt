package so.codex.hawk.domain

import io.reactivex.rxjava3.core.Observable
import so.codex.hawk.domain.providers.ProjectProvider
import so.codex.hawk.entity.Project

/**
 * Class for fetching projects
 * from server.
 */
class FetchProjectsInteractor(private val projectProvider: ProjectProvider) {
    /**
     * Method for fetching projects
     * @return projects
     */
    fun fetchProjects(): Observable<List<Project>> {
        return projectProvider.getProjects()
    }

    /**
     * Update data in provider
     */
    fun update() {
        projectProvider.update()
    }
}