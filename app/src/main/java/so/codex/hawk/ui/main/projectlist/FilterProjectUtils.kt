package so.codex.hawk.ui.main.projectlist

import so.codex.hawk.ui.data.UiProject
import java.util.Locale


// ToDo make class more general for future uses (add generics for example)
// ToDo add docs
object FilterProjectUtils {
    var filterableList: List<UiProject> = listOf()

    fun filter(searchText: String) {
        if (searchText.trim().isEmpty()) return
        if (filterableList.isEmpty()) throw Exception("List for filtering is empty")

        onListFiltered(filterableList.filter {
            it.name.toLowerCase(Locale.ROOT).contains(searchText.toLowerCase(Locale.ROOT))
        })
    }

    private var onListFiltered: ((List<UiProject>) -> Unit) = {}

    fun setOnListFilteredListener(callback: ((List<UiProject>) -> Unit)) {
        onListFiltered = callback
    }
}