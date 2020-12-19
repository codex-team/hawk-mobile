package so.codex.hawk.domain

import io.reactivex.rxjava3.core.Observable
import so.codex.hawk.domain.providers.WorkspaceProvider
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
        return WorkspaceProvider.getWorkspacesCut()
    }

    /**
     * Update data in provider
     */
    fun update() {
        WorkspaceProvider.update()
    }
}