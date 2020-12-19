package so.codex.hawk.ui.main.projectlist

import androidx.recyclerview.widget.DiffUtil
import so.codex.hawk.ui.data.UiProject

/**
 * @see DiffUtil.ItemCallback
 */
class ProjectCallback : DiffUtil.ItemCallback<UiProject>() {

    override fun areItemsTheSame(oldItem: UiProject, newItem: UiProject): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UiProject, newItem: UiProject): Boolean {
        return oldItem == newItem
    }
}