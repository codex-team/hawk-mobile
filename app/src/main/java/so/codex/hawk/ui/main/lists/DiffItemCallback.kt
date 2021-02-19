package so.codex.hawk.ui.main.lists

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

/**
 * Generic class for use as callback in [DiffUtil].
 */
class DiffItemCallback<T : HasId> : DiffUtil.ItemCallback<T>() {

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.id == newItem.id
    }
}