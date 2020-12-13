package so.codex.hawk

import com.apollographql.apollo.rx3.rxQuery
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import so.codex.hawk.entity.Project
import so.codex.hawk.entity.Workspace
import so.codex.hawk.network.NetworkProvider

/**
 * Class for fetching workspaces
 * from server
 */
class FetchWorkspacesInteractor {
    /**
     * Method for fetching workspaces (non-cut)
     * @return non-cut workspaces
     */
    fun fetchWorkspaces(): Observable<List<Workspace>> {
        return NetworkProvider.getApolloClient().rxQuery(WorkspacesQuery())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                it?.data?.workspaces?.map { w ->
                    Workspace(
                        w?.id,
                        w?.name,
                        w?.description,
                        w?.balance?.toString()?.toLong(),
                        w!!.projects?.map { q ->
                            Project(q.id, q.name)
                        }
                    )
                }
            }
    }
}