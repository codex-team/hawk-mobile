package so.codex.hawk.custom.views.badge

import androidx.annotation.ColorRes
import so.codex.hawk.R

/**
 * View model for to display information in the view
 * @property text To show short number with suffix
 * @property count Number of short number. Default -1 [BadgeView.UNDEFINED_COUNT]
 * @property textColor Color of text. Default default color for badge [R.color.badgeTextColor]
 */
data class BadgeViewModel(
    val text: String,
    val count: Long = BadgeView.UNDEFINED_COUNT,
    @ColorRes
    val textColor: Int = R.color.badgeTextColor
)