package so.codex.hawk.custom.views.badge

import androidx.annotation.ColorRes
import so.codex.hawk.R

data class BadgeViewModel(
    val text: String,
    val count: Int = BadgeView.UNDEFINED_COUNT,
    @ColorRes
    val textColor: Int = R.color.badgeTextColor
)