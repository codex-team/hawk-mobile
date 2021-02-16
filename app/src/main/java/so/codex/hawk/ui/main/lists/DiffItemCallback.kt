package so.codex.hawk.ui.main.lists

import androidx.recyclerview.widget.DiffUtil

class DiffItemCallback<T : HasId> : DiffUtil.ItemCallback<T>() {
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.id == newItem.id
    }
}