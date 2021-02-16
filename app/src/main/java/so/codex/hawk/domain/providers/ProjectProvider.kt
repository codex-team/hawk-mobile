package so.codex.hawk.domain.providers

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.Observables
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import so.codex.hawk.entity.Project

class ProjectProvider(
    private val workspaceProvider: WorkspaceProvider,
    externalSourceWorkspace: ExternalSourceWorkspace
) {
    private val subject: BehaviorSubject<List<Project>> = BehaviorSubject.create()

    init {
        Observables.combineLatest(
            workspaceProvider.getWorkspaces(),
            externalSourceWorkspace.selectedWorkspaceObserve()
        )
            .observeOn(Schedulers.io())
            .map { (workspaceList, selectedWorkspace) ->
                if (selectedWorkspace == ExternalSourceWorkspace.NO_SELECTED_WORKSPACE) {
                    workspaceList.fold(listOf()) { projects, workspace ->
                        projects + workspace.projects
                    }
                } else {
                    workspaceList
                        .find { it.id == selectedWorkspace.id }
                        ?.projects
                }
            }
            .subscribe { projectList ->
                subject.onNext(projectList)
            }
    }

    fun getProjects(): Observable<List<Project>> {
        return subject.hide()
    }

    fun update() {
        workspaceProvider.update()
    }
}