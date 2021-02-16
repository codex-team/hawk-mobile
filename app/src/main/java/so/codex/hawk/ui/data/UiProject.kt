package so.codex.hawk.ui.data

import so.codex.hawk.custom.views.SquircleDrawable
import so.codex.hawk.custom.views.badge.UiBadgeViewModel
import so.codex.hawk.ui.main.lists.HasId

/**
 * Ui project model for recyclerView.
 * @see so.codex.hawk.entity.Project
 */
data class UiProject(
    override val id: String,
    val name: String,
    val description: String,
    val image: String = "",
    val imageDrawable: SquircleDrawable,
    val badgeModel: UiBadgeViewModel = UiBadgeViewModel()
):HasId