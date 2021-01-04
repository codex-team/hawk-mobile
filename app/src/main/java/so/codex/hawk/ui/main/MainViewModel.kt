package so.codex.hawk.ui.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.Observables
import io.reactivex.rxjava3.subjects.PublishSubject
import so.codex.hawk.HawkApp
import so.codex.hawk.custom.views.badge.BadgeViewModel
import so.codex.hawk.domain.FetchProjectsInteractor
import so.codex.hawk.domain.FetchWorkspacesInteractor
import so.codex.hawk.notification.domain.NotificationManager
import so.codex.hawk.notification.model.NotificationModel
import so.codex.hawk.notification.model.NotificationType
import so.codex.hawk.ui.data.UiMainViewModel
import so.codex.hawk.ui.data.UiProject
import so.codex.hawk.ui.data.UiWorkspace
import so.codex.hawk.utils.ShortNumberUtils

/**
 * The ViewModel class for MainActivity.
 * Designed to handle various processes associated with the specified activity.
 */
class MainViewModel : ViewModel() {
    /**
     * @see Context
     */
    private var context: Context = HawkApp.getContext()

    /**
     * A LiveData of [UiMainViewModel] that should be inserted to the view
     */
    private val uiModels: MutableLiveData<UiMainViewModel> = MutableLiveData()

    /**
     * Subject of ui event for notify a component of a new event
     */
    private val eventSubject = PublishSubject.create<UiEvent>()

    /**
     * @property fetchWorkspaceInteractor for getting workspaces
     */
    private val fetchWorkspaceInteractor = FetchWorkspacesInteractor()

    /**
     * @property fetchProjectInteractor for getting projects
     */
    private val fetchProjectInteractor = FetchProjectsInteractor()

    /**
     * Contain all disposable of sources
     */
    private val disposable = CompositeDisposable()

    /**
     * Initialization block. Called on creation.
     */
    init {
        disposable.addAll(
            subscribeOnData(),
            subscribeOnEvent(),
        )

        eventSubject.onNext(UiEvent.Refresh)
    }

    /**
     * Subscribe on events from ui and handle it
     *
     * @return [Disposable] for dispose of observer from source
     */
    private fun subscribeOnEvent(): Disposable {
        return eventSubject.subscribe { event ->
            when (event) {
                is UiEvent.Refresh -> {
                    fetchWorkspaceInteractor.update()
                }
            }
        }
    }

    /**
     * Subscribe on events from ui like as [UiEvent.Refresh] and data from the source of workspace.
     * Map pair of ui event and workspace to ui model for showing on activity
     *
     * @return [Disposable] for dispose of observer from source
     */
    private fun subscribeOnData(): Disposable {
        return Observables.combineLatest(
            getRefreshedSubject(),
            fetchWorkspaceInteractor.fetchWorkspaces()
                .map {
                    it.map { workspace ->
                        UiWorkspace(workspace.name)
                    }
                }
                .doAfterNext {
                    eventSubject.onNext(UiEvent.CompleteRefresh)
                },
            fetchProjectInteractor.fetchProjects()
                .map { projectList ->
                    projectList.map {
                        val item =
                            UiProject(
                                it.id,
                                it.name,
                                it.description,
                                it.image,
                                it.unreadCount.toBadge()
                            )
                        if (item.image.isBlank()) {
                            item.createDefaultLogo(context)
                        }
                        item
                    }
                }
        )
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { (isFetched, workspaceList, projectList) ->
                    uiModels.value =
                        UiMainViewModel(workspaceList, projectList, showLoading = isFetched)
                },
                {
                    it.printStackTrace()
                    NotificationManager.showNotification(
                        NotificationModel(
                            text = it.message ?: "Unknown error in while getting workspace",
                            type = NotificationType.ERROR
                        )
                    )
                }
            )
    }

    /**
     * Extension for converting from number to [BadgeViewModel]
     * @return Badge view model for current number
     */
    private fun Int.toBadge(): BadgeViewModel {
        if (this == 0) {
            return BadgeViewModel()
        }
        return BadgeViewModel(
            ShortNumberUtils.convert(this.toLong()),
            this.toLong()
        )
    }

    /**
     * Get source of ui event and map if ui events is [UiEvent.Refresh]
     *
     * @return Observable of Boolean, if we receive ui event for refresh data then source contains
     * true else false
     */
    private fun getRefreshedSubject(): Observable<Boolean> {
        return eventSubject
            .filter { event ->
                event is UiEvent.Refresh || event is UiEvent.CompleteRefresh
            }.map { refreshEvent ->
                refreshEvent is UiEvent.Refresh
            }
    }

    /**
     * Submit ui event to event source
     *
     * @param event is [UiEvent] for handle some of the action
     */
    fun submitEvent(event: UiEvent) {
        eventSubject.onNext(event)
    }

    /**
     * Dispose of all sources
     */
    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

    /**
     * Provide source of model
     */
    fun observeUiModels(): LiveData<UiMainViewModel> {
        return uiModels
    }

    /**
     * Common class for ui events
     */
    sealed class UiEvent {
        /**
         * Ui event for refresh data (like as swipe to refresh)
         */
        object Refresh : UiEvent()

        /**
         * An ui event that indicates an refresh has been completed
         */
        object CompleteRefresh : UiEvent()
    }
}