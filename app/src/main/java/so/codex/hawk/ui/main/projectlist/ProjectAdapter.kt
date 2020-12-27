package so.codex.hawk.ui.main.projectlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.project_list_item.view.description
import kotlinx.android.synthetic.main.project_list_item.view.events
import kotlinx.android.synthetic.main.project_list_item.view.icon
import kotlinx.android.synthetic.main.project_list_item.view.name
import so.codex.hawk.R
import so.codex.hawk.ui.data.UiProject
import java.util.ArrayList
import java.util.Locale

/**
 * Adapter for projects list.
 */
class ProjectAdapter : ListAdapter<UiProject, ProjectAdapter.ViewHolder>(ProjectCallback()),
    Filterable {

    /**
     * Current list
     */
    private var filterableList: MutableList<UiProject> = mutableListOf()

    /**
     * List that contains all items (unfiltered list)
     */
    private var cacheList: MutableList<UiProject> = mutableListOf()

    /**
     * override method for adding new items
     *
     * @see ListAdapter
     */
    override fun submitList(list: List<UiProject>?) {
        if (list != null)
            submitList(list.toMutableList(), isNeedCache = true)
    }

    /**
     * Method for adding new items and
     * check if these items need to cache
     *
     * @param list List to submit
     * @param isNeedCache decide to cache list
     */
    fun submitList(list: MutableList<UiProject>?, isNeedCache: Boolean = false) {
        list?.let {
            if (isNeedCache) cacheList = list
            filterableList = list
        }
        super.submitList(filterableList)
    }

    /**
     * ViewHolder creation method
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.project_list_item, parent, false)
        return ViewHolder(view)
    }

    /**
     * Method for filling ViewHolder with data
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    /**
     * @see RecyclerView.ViewHolder
     */
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val iconImageView = itemView.icon
        private val titleTextView = itemView.name
        private val descriptionTextView = itemView.description
        private val eventsTextView = itemView.events
        fun bind(item: UiProject) {
            titleTextView.text = item.name
            descriptionTextView.text = item.description
            eventsTextView.text = item.abbreviationEvents
            if (item.image.isNotEmpty()) {
                Picasso.get().load(item.image).into(iconImageView)
            } else {
                iconImageView.setImageBitmap(item.getDefaultIcon(iconImageView))
            }
            eventsTextView.visibility = if (item.unreadCount == 0) {
                View.INVISIBLE
            } else {
                View.VISIBLE
            }
        }
    }

    /**
     * Get filter for filter items
     * and then publish it
     * without caching them
     *
     * @see Filter for understand methods
     * @return filter to filter items
     */
    @Suppress("UNCHECKED_CAST")
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
                submitList(results!!.values as ArrayList<UiProject>, isNeedCache = false)
            }

        }
    }
}