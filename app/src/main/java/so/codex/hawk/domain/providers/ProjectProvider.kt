package so.codex.hawk.domain.providers

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import so.codex.hawk.entity.Project

class ProjectProvider(workspaceProvider: WorkspaceProvider) {
    private val subject: BehaviorSubject<List<Project>> = BehaviorSubject.create()

    init {
        workspaceProvider.getWorkspaces()
            .observeOn(Schedulers.io())
            .map {
                it.fold(listOf<Project>()) { projects, workspace ->
                    projects + workspace.projects
                }
            }
            .subscribe { projectList ->
                subject.onNext(projectList)
            }
    }

    fun getProjects(): Observable<List<Project>> {
        return subject.hide()
    }
}