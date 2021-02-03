package so.codex.hawk.ui.main.projectlist

import android.widget.Filter
import android.widget.Filterable
import so.codex.hawk.ui.data.UiProject
import java.util.Locale


// ToDo make class more general for future uses (add generics for example)
// ToDo add docs
object FilterProjectUtils : Filterable {
    private var cacheList: MutableList<UiProject> = mutableListOf()
    private var filterableList: MutableList<UiProject> = mutableListOf()

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val results = FilterResults()
                if (constraint.isNullOrEmpty() || constraint.trim().isEmpty()) {
                    filterableList = cacheList
                } else {
                    val list = mutableListOf<UiProject>()
                    cacheList.let {
                        for (project in it) {
                            if (project.name.trim().toLowerCase(Locale.ROOT).contains(
                                    constraint.toString().toLowerCase(
                                        Locale.ROOT
                                    )
                                )
                            ) {
                                list.add(project)
                            }
                        }
                        filterableList = list
                    }
                }

                results.values = filterableList

                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                onListFiltered(results!!.values as List<UiProject>)
            }

        }
    }

    private var onListFiltered: ((List<UiProject>) -> Unit) = {}

    fun setOnListFilteredListener(callback: ((List<UiProject>) -> Unit)) {
        onListFiltered = callback
    }

    // ToDo ui model as argument for future uses
    fun addList(projects: List<UiProject>) {
        filterableList = projects.toMutableList()
        cacheList = projects.toMutableList()
    }
}