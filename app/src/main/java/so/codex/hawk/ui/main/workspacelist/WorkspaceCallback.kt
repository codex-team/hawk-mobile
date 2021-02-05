package so.codex.hawk.ui.main.workspacelist

import androidx.recyclerview.widget.DiffUtil
import so.codex.hawk.ui.data.UiWorkspace

/**
 * @see DiffUtil.ItemCallback
 */
class WorkspaceCallback : DiffUtil.ItemCallback<UiWorkspace>() {
    override fun areItemsTheSame(oldItem: UiWorkspace, newItem: UiWorkspace): Boolean {
        return oldItem.workspaceName == newItem.workspaceName
    }

    override fun areContentsTheSame(oldItem: UiWorkspace, newItem: UiWorkspace): Boolean {
        return oldItem.workspaceName == newItem.workspaceName
    }
}