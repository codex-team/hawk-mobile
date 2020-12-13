package so.codex.hawk.data_providers

import so.codex.hawk.entity.Project

object ProjectProvider {
    var projects: List<Project?>? = null
    fun supply(data: List<Project?>?) {
        projects = data
    }
}