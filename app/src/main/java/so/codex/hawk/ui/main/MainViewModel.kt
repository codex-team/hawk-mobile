package so.codex.hawk.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import so.codex.hawk.FetchWorkspacesInteractor
import so.codex.hawk.domain.main.MainEvent

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

    /**
     * @property interactor for getting workspaces
     */
    private val interactor = FetchWorkspacesInteractor()

    /**
     * Initialization block. Called on creation.
     */
    init {
        interactor.fetchWorkspaces()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    mainEvent.value = MainEvent.WorkspacesSuccessEvent(it)
                }, {
                    it.printStackTrace()
                }
            )
    }

    /**
     * Method to provide LiveData [mainEvent] for observers.
     *
     * @return [LiveData] to monitor [MainEvent].
     */
    fun observeMainEvent(): LiveData<MainEvent> {
        return mainEvent
    }
}