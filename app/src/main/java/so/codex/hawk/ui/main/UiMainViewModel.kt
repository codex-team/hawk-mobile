package so.codex.hawk.ui.main

import so.codex.hawk.ui.data.UiWorkspace

/**
 * Common class for representation information of activity
 * @param workspaces contains list of [UiWorkspace] for inserting in recycler view of workspace
 * @param showLoading if need to show loading progress or not
 */
data class UiMainViewModel(
    val workspaces: List<UiWorkspace>,
    val showLoading: Boolean
)