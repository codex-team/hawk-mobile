package so.codex.hawk.data_providers

import so.codex.hawk.entity.WorkspaceCut

object WorkspaceProvider {
    var workspaces: List<WorkspaceCut>? = null

    fun supply(data: List<WorkspaceCut>) {
        workspaces = data
    }
}
