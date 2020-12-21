package so.codex.hawk.ui.main

import so.codex.hawk.ui.data.UiWorkspace

data class UiMainViewModel(
    val workspaces: List<UiWorkspace>,
    val showLoading: Boolean
)