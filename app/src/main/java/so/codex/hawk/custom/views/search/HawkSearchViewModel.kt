package so.codex.hawk.custom.views.search

/**
 * Model for updating [HawkSearchView]
 *
 * @param hint text to display if the user has not entered any text in the search
 * @param text text that user entered in the search,
 * @param listener callback for notify if the text in the search was changed
 */
data class HawkSearchViewModel(
    val hint: String,
    val text: String,
    val listener: (String) -> Unit
)