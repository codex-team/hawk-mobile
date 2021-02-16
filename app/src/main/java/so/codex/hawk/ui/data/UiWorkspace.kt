package so.codex.hawk.ui.data

import so.codex.hawk.custom.views.SquircleDrawable
import so.codex.hawk.ui.main.lists.HasId

/**
 * Representation of model for showing information on Workspace item in the recycler view
 */
data class UiWorkspace(
    override val id: String,
    val workspaceName: String,
    val imageDrawable: SquircleDrawable,
    val isSelected: Boolean = false,
    val onClick: () -> Unit
):HasId {
    /**
     * Yes, I know that these methods are generated automatically for data classes.
     * I just need a different behavior since there is no sane way to compare onClick
     * (always returns false) and so far SquircleDrawable has no equals.
     */
    override fun equals(other: Any?): Boolean {
        if (other == null || other !is UiWorkspace) return false
        if (this === other) return true
        return id == other.id
            && workspaceName == other.workspaceName
            && isSelected == other.isSelected
    }

    /**
     * @see equals
     */
    override fun hashCode(): Int {
        return id.hashCode() + workspaceName.hashCode()+ isSelected.hashCode()
    }
}