package so.codex.hawk.data_providers

import com.apollographql.apollo.rx3.rxQuery
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import so.codex.hawk.WorkspacesQuery
import so.codex.hawk.entity.Workspace
import so.codex.hawk.entity.WorkspaceCut
import so.codex.hawk.extensions.mapNotNull
import so.codex.hawk.network.NetworkProvider
import so.codex.hawk.notification.domain.NotificationManager
import so.codex.hawk.notification.model.NotificationModel
import so.codex.hawk.notification.model.NotificationType
import timber.log.Timber

/**
 * Singleton which has only workspaces
 */
object WorkspaceProvider {
    /**
     * @property generalSource source for emitting items
     */
    val responseSource: BehaviorSubject<List<Workspace>> = BehaviorSubject.create()

    val updateSubject: PublishSubject<Unit> = PublishSubject.create()

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
        return responseSource
            .subscribeOn(Schedulers.io())
            .mapNotNull {
                it.map { w ->
                    w.toCut()
                }
            }
    }

    /**
     * Refresh fetching workspaces
     */
    fun update() {
        updateSubject.onNext(Unit)
    }

    /**
     * Fetch workspaces and
     * put them in [generalSource]
     */
    private fun fetchWorks() {
        updateSubject.switchMap {
            NetworkProvider.getApolloClient().rxQuery(WorkspacesQuery())
                .subscribeOn(Schedulers.io())
                .mapNotNull {
                    it.data?.workspaces?.mapNotNull { w ->
                        w?.let {
                            Workspace(
                                w.id,
                                w.name ?: "",
                                w.description ?: "",
                                w.balance.toString().toLong()
                            )
                        }
                    }
                }
                .doOnError {
                    Timber.e(it)
                    NotificationManager.showNotification(
                        NotificationModel(
                            text = it.message ?: "Unknown error in while getting workspace",
                            type = NotificationType.ERROR
                        )
                    )
                }
        }.subscribe {
            responseSource.onNext(it)
        }
    }

    /**
     * Function for mapping to workspaces without projects
     * @return Workspace without projects
     */
    private fun Workspace.toCut(): WorkspaceCut {
        return WorkspaceCut(id, name, description, balance)
    }
}