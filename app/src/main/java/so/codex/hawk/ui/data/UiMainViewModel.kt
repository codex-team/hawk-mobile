package so.codex.hawk.ui.data

/**
 * Common class for representation information of activity
 * @param projects contains list of [UiWorkspace] for inserting in recycler view of workspace
 * @param projects contains list of [UiProject] for inserting in recycler view of projects
 * @param showLoading if need to show loading progress or not
 */
data class UiMainViewModel(
    val title: String,
    val workspaces: List<UiWorkspace>,
    val projects: List<UiProject>,
    val showLoading: Boolean
)