package so.codex.hawk.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import so.codex.hawk.data_providers.WorkspaceProvider
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
     * Initialization block. Called on creation.
     */
    init {
        WorkspaceProvider.getWorkspaces().subscribe(
            {
                mainEvent.value = MainEvent.WorkspacesSuccessEvent(it)
            },
            {
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