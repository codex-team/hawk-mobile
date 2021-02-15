package so.codex.hawk.ui.data

import so.codex.hawk.custom.views.search.HawkSearchUiViewModel

/**
 * Common class for representation information of activity
 * @param workspaces contains list of [UiWorkspace] for inserting in recycler view of workspace
 * @param showLoading if need to show loading progress or not
 * @param searchUiViewModel contain model for updating search view
 */
data class UiMainViewModel(
    val title: String,
    val workspaces: List<UiWorkspace>,
    val showLoading: Boolean,
    val searchUiViewModel: HawkSearchUiViewModel
)