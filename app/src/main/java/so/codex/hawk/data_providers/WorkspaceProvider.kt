package so.codex.hawk.data_providers

import com.apollographql.apollo.rx3.rxQuery
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import so.codex.hawk.WorkspacesQuery
import so.codex.hawk.entity.Workspace
import so.codex.hawk.entity.WorkspaceCut
import so.codex.hawk.extensions.mapNotNull
import so.codex.hawk.extensions.toCut
import so.codex.hawk.network.NetworkProvider

/**
 * Singleton which has only workspaces
 */
object WorkspaceProvider {
    /**
     * @property generalSource source for emitting items
     */
    var generalSource: PublishSubject<List<Workspace>> = PublishSubject.create()


    /**
     * Init block
     */
    init {
        fetchWorks()
    }

    /**
     * Method for getting workspaces
     *
     * @return workspaces without projects inside
     */
    fun getWorkspaces(): Observable<List<WorkspaceCut>> {
        return generalSource
            .subscribeOn(Schedulers.io())
            .mapNotNull {
                it?.map { w ->
                    w.toCut()
                }
            }
    }

    /**
     * Refresh fetching workspaces
     */
    fun update() {
        fetchWorks()
    }

    /**
     * Fetch workspaces and
     * put them in [generalSource]
     */
    private fun fetchWorks() {
        NetworkProvider.getApolloClient().rxQuery(WorkspacesQuery())
            .subscribeOn(Schedulers.io())
            .mapNotNull {
                it.data?.workspaces?.map { w ->
                    Workspace(
                        w?.id,
                        w?.name,
                        w?.description,
                        w?.balance.toString().toLong()
                    )
                }
            }.subscribe(generalSource)
    }
}