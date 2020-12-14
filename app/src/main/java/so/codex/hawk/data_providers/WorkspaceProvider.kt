package so.codex.hawk.data_providers

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import so.codex.hawk.FetchWorkspacesInteractor
import so.codex.hawk.entity.Project
import so.codex.hawk.entity.WorkspaceCut
import so.codex.hawk.extensions.toCut

object WorkspaceProvider {

    private val interactor = FetchWorkspacesInteractor()

    fun getWorkspaces(): Observable<List<WorkspaceCut>> {
        return interactor.fetchWorkspaces()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { list ->
                val l = mutableListOf<Project>()
                list.forEach {
                    it.projects?.let { pl -> l.addAll(pl) }
                }
                ProjectProvider.supply(l)
                list.map { it.toCut() }
            }
    }
}
