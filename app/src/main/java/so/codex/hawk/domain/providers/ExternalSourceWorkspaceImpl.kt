package so.codex.hawk.domain.providers

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import so.codex.hawk.entity.WorkspaceCut

class ExternalSourceWorkspaceImpl : ExternalSourceWorkspace {
    private val currentWorkspaceSubject = BehaviorSubject.create<WorkspaceCut>()

    init {
        currentWorkspaceSubject.onNext(ExternalSourceWorkspace.NO_SELECTED_WORKSPACE)
    }

    override fun selectedWorkspaceObserve(): Observable<WorkspaceCut> {
        return currentWorkspaceSubject.hide()
    }

    override fun setSelectedWorkspace(workspace: WorkspaceCut) {
        if (currentWorkspaceSubject.value == workspace) {
            currentWorkspaceSubject.onNext(ExternalSourceWorkspace.NO_SELECTED_WORKSPACE)
        } else {
            currentWorkspaceSubject.onNext(workspace)
        }
    }

    override fun isWorkspaceSelected(): Boolean {
        return currentWorkspaceSubject.value != ExternalSourceWorkspace.NO_SELECTED_WORKSPACE
    }
}