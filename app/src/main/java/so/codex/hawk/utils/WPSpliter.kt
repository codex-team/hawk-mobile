package so.codex.hawk.utils

import so.codex.hawk.data_providers.ProjectProvider
import so.codex.hawk.data_providers.WorkspaceProvider
import so.codex.hawk.entity.Project
import so.codex.hawk.entity.Workspace
import so.codex.hawk.extensions.toCut

object WPSpliter {
    fun split(data: List<Workspace>) {
        val projects: MutableList<Project> = mutableListOf()
        WorkspaceProvider.supply(data.map {
            it.projects?.let { l -> projects.addAll(l) }
            it.toCut()
        })
        ProjectProvider.supply(projects)
    }
}