package so.codex.hawk.data_providers

import so.codex.hawk.entity.Project

/**
 * Singleton for accessing projects
 */
object ProjectProvider {
    /**
     * @property projects current projects
     */
    var projects: List<Project?>? = null

    /**
     * Method for adding data
     * @param data current projects to store
     */
    fun supply(data: List<Project?>?) {
        projects = data
    }
}