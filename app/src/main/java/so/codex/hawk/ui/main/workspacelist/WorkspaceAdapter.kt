package so.codex.hawk.ui.main.workspacelist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.workspace_list_item.view.workspace_icon
import kotlinx.android.synthetic.main.workspace_list_item.view.workspace_name
import so.codex.hawk.HawkApp
import so.codex.hawk.R
import so.codex.hawk.ui.data.UiWorkspace

class WorkspaceAdapter(val listener: OnWorkspaceClickListener) :
    ListAdapter<UiWorkspace, WorkspaceAdapter.ViewHolder>(WorkspaceCallback()) {

    /**
     * @property selectedBackground drawable for selectedView
     */
    private val selectedBackground =
        ContextCompat.getDrawable(HawkApp.context, R.drawable.workspace_select_background)

    /**
     * @property selectedView Used for change background.
     */
    private var selectedView: View? = null

    /**
     * ViewHolder creation method
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.workspace_list_item, parent, false)
        return ViewHolder(view).apply {
            view.setOnClickListener(this)
        }
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
    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        private var workspaceId: String = ""
        private val workspaceIcon = itemView.workspace_icon
        private val workspaceName = itemView.workspace_name

        fun bind(item: UiWorkspace) {
            workspaceId = item.workspaceId
            workspaceIcon.setImageDrawable(item.imageDrawable)
            workspaceName.text = item.workspaceName
        }

        override fun onClick(v: View?) {
            listener.onWorkspaceClick(workspaceId)
            v?.let { view ->
                if (view.background == null) {
                    selectedView?.let {
                        it.background = null
                    }
                    view.background = selectedBackground
                    selectedView = view
                } else {
                    view.background = null
                    selectedView = null
                }
            }
        }
    }

    /**
     * Interface for listen on workspace click
     */
    interface OnWorkspaceClickListener {
        fun onWorkspaceClick(workspaceId: String)
    }
}