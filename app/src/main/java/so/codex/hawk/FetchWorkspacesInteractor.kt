package so.codex.hawk

import io.reactivex.rxjava3.core.Observable
import so.codex.hawk.data_providers.WorkspaceProvider
import so.codex.hawk.entity.WorkspaceCut

/**
 * Class for fetching workspaces
 * from server
 */
class FetchWorkspacesInteractor {
    /**
     * Method for fetching workspaces (non-cut)
     * @return cut workspaces
     */
    fun fetchWorkspaces(): Observable<List<WorkspaceCut>> {
        return WorkspaceProvider.getWorkspaces()
    }

    fun update() {
        WorkspaceProvider.update()
    }
}