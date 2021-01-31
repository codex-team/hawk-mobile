package so.codex.hawk.ui.data

import so.codex.hawk.custom.views.SquircleDrawable
import so.codex.hawk.custom.views.badge.UiBadgeViewModel

/**
 * Ui project model for recyclerView.
 * @see so.codex.hawk.entity.Project
 */
data class UiProject(
    val id: String,
    val name: String,
    val description: String,
    val image: String = "",
    val imageDrawable: SquircleDrawable,
    val badgeModel: UiBadgeViewModel = UiBadgeViewModel()
)