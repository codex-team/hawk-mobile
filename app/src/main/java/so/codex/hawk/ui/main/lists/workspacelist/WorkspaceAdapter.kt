package so.codex.hawk.ui.main.lists.workspacelist

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.workspace_list_item.view.workspace_icon
import kotlinx.android.synthetic.main.workspace_list_item.view.workspace_name
import so.codex.hawk.R
import so.codex.hawk.ui.data.UiWorkspace
import so.codex.hawk.ui.main.lists.DiffItemCallback

class WorkspaceAdapter :
    ListAdapter<UiWorkspace, WorkspaceAdapter.ViewHolder>(DiffItemCallback<UiWorkspace>()) {

    /**
     * @property selectedBackground drawable for selectedView
     */
    private var selectedBackground: Drawable? = null

    /**
     * ViewHolder creation method
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.workspace_list_item, parent, false)
        if (selectedBackground == null) {
            selectedBackground =
                ContextCompat.getDrawable(view.context, R.drawable.workspace_select_background)
        }
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
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val workspaceIcon = itemView.workspace_icon
        private val workspaceName = itemView.workspace_name

        fun bind(item: UiWorkspace) {
            workspaceIcon.setImageDrawable(item.imageDrawable)
            workspaceName.text = item.workspaceName
            itemView.background = if (item.isSelected) selectedBackground else null
            itemView.setOnClickListener {
                item.onClick()
            }
        }
    }
}