package so.codex.hawk.ui.main.projectlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.project_list_item.view.badge_count
import kotlinx.android.synthetic.main.project_list_item.view.description
import kotlinx.android.synthetic.main.project_list_item.view.icon
import kotlinx.android.synthetic.main.project_list_item.view.name
import so.codex.hawk.R
import so.codex.hawk.ui.data.UiProject

/**
 * Adapter for projects list.
 */
class ProjectAdapter : ListAdapter<UiProject, ProjectAdapter.ViewHolder>(ProjectCallback()) {

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
        private val badgeView = itemView.badge_count

        fun bind(item: UiProject) {
            titleTextView.text = item.name
            descriptionTextView.text = item.description
            badgeView.update(item.badgeModel)
            iconImageView.setImageDrawable(item.imageDrawable)
            badgeView.update(item.badgeModel)
        }
    }
}