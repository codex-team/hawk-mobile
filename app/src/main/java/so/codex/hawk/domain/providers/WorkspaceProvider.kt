package so.codex.hawk.domain.providers

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.rx3.rxQuery
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import so.codex.hawk.WorkspacesQuery
import so.codex.hawk.entity.Workspace
import so.codex.hawk.entity.WorkspaceCut
import so.codex.hawk.extensions.apollo.toWorkspace
import so.codex.hawk.extensions.mapNotNull
import timber.log.Timber
import javax.inject.Inject

/**
 * Singleton which has only workspaces
 */
class WorkspaceProvider @Inject constructor(
    private val client: ApolloClient
) {
    /**
     * Source for emitting response from api
     */
    private val responseSource: BehaviorSubject<List<Workspace>> = BehaviorSubject.create()

    /**
     * Event for fetching new data from api
     */
    private val updateSubject: PublishSubject<Unit> = PublishSubject.create()

    /**
     * Subscribe on main observable for sending and handling api response
     */
    init {
        fetchWorks()
    }

    /**
     * Method for getting workspaces cut.
     *
     * @return workspaces without projects inside
     */
    fun getWorkspacesCut(): Observable<List<WorkspaceCut>> {
        return responseSource
            .subscribeOn(Schedulers.io())
            .mapNotNull {
                it.map { w ->
                    w.toCut()
                }
            }
    }

    /**
     * Method for getting workspaces
     *
     * @return full workspaces.
     */
    fun getWorkspaces(): Observable<List<Workspace>> {
        return responseSource.hide()
    }

    /**
     * Refresh fetching workspaces
     */
    fun update() {
        updateSubject.onNext(Unit)
    }

    /**
     * Fetch workspaces and put them in [responseSource]
     */
    private fun fetchWorks() {
        updateSubject.switchMap {
            client.rxQuery(WorkspacesQuery())
                .subscribeOn(Schedulers.io())
                .mapNotNull {
                    it.data?.workspaces?.mapNotNull { w ->
                        w?.toWorkspace()
                    }
                }
        }
            .subscribe(
                {
                    responseSource.onNext(it)
                },
                {
                    Timber.e(it)
                }
            )
    }

    /**
     * Function for mapping to workspaces without projects
     * @return Workspace without projects
     */
    private fun Workspace.toCut(): WorkspaceCut {
        return WorkspaceCut(id, name, image, description, balance)
    }
}