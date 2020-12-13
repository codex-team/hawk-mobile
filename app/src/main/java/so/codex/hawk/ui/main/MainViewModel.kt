package so.codex.hawk.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import so.codex.hawk.FetchWorkspacesInteractor
import so.codex.hawk.domain.main.MainEvent
import so.codex.hawk.utils.WPSpliter
import so.codex.hawk.data_providers.WorkspaceProvider

class MainViewModel : ViewModel() {

    private val liveData: MutableLiveData<MainEvent> = MutableLiveData()
    private val interactor = FetchWorkspacesInteractor()

    init {
        interactor.fetchWorkspaces().subscribe(
            {
                WPSpliter.split(it)
                liveData.value = MainEvent.WorkspacesSuccessEvent(WorkspaceProvider.workspaces!!)
            }, {
                it.printStackTrace()
            })
    }

    fun observeMainEvent(): LiveData<MainEvent> {
        return liveData
    }
}