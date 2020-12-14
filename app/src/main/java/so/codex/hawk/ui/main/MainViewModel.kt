package so.codex.hawk.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import so.codex.hawk.data_providers.WorkspaceProvider
import so.codex.hawk.domain.main.MainEvent

class MainViewModel : ViewModel() {

    private val liveData: MutableLiveData<MainEvent> = MutableLiveData()

    init {
        WorkspaceProvider.getWorkspaces().subscribe({
            liveData.value = MainEvent.WorkspacesSuccessEvent(it)
        }, {
            it.printStackTrace()
        })
    }

    fun observeMainEvent(): LiveData<MainEvent> {
        return liveData
    }
}