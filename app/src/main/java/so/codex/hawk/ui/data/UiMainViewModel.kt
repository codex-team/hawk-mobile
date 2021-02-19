package so.codex.hawk.ui.data

/**
 * Common class for representation information of activity
 * @param title for the screen.
 * @param showLoading if need to show loading progress or not
 */
data class UiMainViewModel(
    val title: String,
    val showLoading: Boolean
)