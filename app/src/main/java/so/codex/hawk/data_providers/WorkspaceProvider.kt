package so.codex.hawk.data_providers

import com.apollographql.apollo.rx3.rxQuery
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import so.codex.hawk.WorkspacesQuery
import so.codex.hawk.entity.Workspace
import so.codex.hawk.entity.WorkspaceCut
import so.codex.hawk.extensions.toCut
import so.codex.hawk.network.NetworkProvider

/**
 * Singleton which has only workspaces
 */
object WorkspaceProvider {

    /**
     * Subject for emitting items
     */
    private val behaviorSubject: BehaviorSubject<List<WorkspaceCut>> = BehaviorSubject.create()

    /**
     * Method for getting workspaces
     * @return workspaces without projects inside
     */
    fun getWorkspaces(): Observable<List<WorkspaceCut>> {
        return behaviorSubject.subscribeOn(Schedulers.io())
            .switchMap {
                NetworkProvider.getApolloClient().rxQuery(WorkspacesQuery())
                    .subscribeOn(Schedulers.io())
                    .map {
                        it.data?.workspaces?.mapNotNull { w ->
                            Workspace(
                                w?.id,
                                w?.name,
                                w?.description,
                                w?.balance.toString().toLong()
                            ).toCut()
                        }
                    }
            }
    }
}