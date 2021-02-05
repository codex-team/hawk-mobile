package so.codex.hawk.ui.data

import so.codex.hawk.custom.views.SquircleDrawable

/**
 * Representation of model for showing information on Workspace item in the recycler view
 */
data class UiWorkspace(
    val workspaceId: String,
    val workspaceName: String,
    val imageDrawable: SquircleDrawable
)