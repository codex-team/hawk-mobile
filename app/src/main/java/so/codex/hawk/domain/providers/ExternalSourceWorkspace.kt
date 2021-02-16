package so.codex.hawk.domain.providers

import io.reactivex.rxjava3.core.Observable
import so.codex.hawk.entity.WorkspaceCut

interface ExternalSourceWorkspace {
    companion object {
        val NO_SELECTED_WORKSPACE: WorkspaceCut = WorkspaceCut()
    }

    fun selectedWorkspaceObserve(): Observable<WorkspaceCut>
    fun setSelectedWorkspace(workspace: WorkspaceCut)
    fun isWorkspaceSelected(): Boolean
}