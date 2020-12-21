package so.codex.hawk.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.Observables
import io.reactivex.rxjava3.subjects.PublishSubject
import so.codex.hawk.FetchWorkspacesInteractor
import so.codex.hawk.domain.main.MainEvent
import so.codex.hawk.ui.data.UiWorkspace
import timber.log.Timber

/**
 * The ViewModel class for MainActivity.
 * Designed to handle various processes associated with the specified activity.
 */
class MainViewModel : ViewModel() {
    /**
     * @property mainEvent A LiveData object to reactively pass a MainEvent to the View.
     * @see LiveData
     */
    private val mainEvent: MutableLiveData<MainEvent> = MutableLiveData()

    private val uiModels: MutableLiveData<UiMainViewModel> = MutableLiveData()

    private val eventSubject = PublishSubject.create<UiEvent>()

    /**
     * @property interactor for getting workspaces
     */
    private val interactor = FetchWorkspacesInteractor()

    private val disposable = CompositeDisposable()

    /**
     * Initialization block. Called on creation.
     */
    init {
        Timber.e("#info create viewModel")
        disposable.addAll(
            subscribeOnData(),
            subscribeOnEvent(),
        )

        eventSubject.onNext(UiEvent.Refresh)
    }

    private fun subscribeOnEvent(): Disposable {
        return eventSubject.subscribe { event ->
            when (event) {
                is UiEvent.Refresh -> {
                    interactor.update()
                }
            }
        }
    }

    private fun subscribeOnData(): Disposable {
        return Observables.combineLatest(
            getRefreshedSubject(),
            interactor.fetchWorkspaces()
                .map {
                    it.map { workspace ->
                        UiWorkspace(workspace.name)
                    }
                }
                .doAfterNext {
                    eventSubject.onNext(UiEvent.CompleteRefresh)
                }
        )
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { (isFetched, workspaceList) ->
                    uiModels.value =
                        UiMainViewModel(workspaces = workspaceList, showLoading = isFetched)
                },
                {
                    it.printStackTrace()
                }
            )
    }

    private fun getRefreshedSubject(): Observable<Boolean> {
        return eventSubject
            .filter { event ->
                event is UiEvent.Refresh || event is UiEvent.CompleteRefresh
            }.map { refreshEvent ->
                refreshEvent is UiEvent.Refresh
            }
    }

    public fun submitEvent(event: UiEvent) {
        eventSubject.onNext(event)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

    /**
     * Method to provide LiveData [mainEvent] for observers.
     *
     * @return [LiveData] to monitor [MainEvent].
     */
    fun observeMainEvent(): LiveData<MainEvent> {
        return mainEvent
    }

    fun observeUiModels(): LiveData<UiMainViewModel> {
        return uiModels
    }

    sealed class UiEvent {
        object Refresh : UiEvent()
        object CompleteRefresh : UiEvent()
    }
}