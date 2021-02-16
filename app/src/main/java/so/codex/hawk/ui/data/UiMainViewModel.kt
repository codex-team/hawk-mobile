package so.codex.hawk.ui.data

import so.codex.hawk.custom.views.search.HawkSearchUiViewModel

/**
 * Common class for representation information of activity
 * @param title
 * @param showLoading if need to show loading progress or not
 */
data class UiMainViewModel(
    val title: String,
    val showLoading: Boolean
)