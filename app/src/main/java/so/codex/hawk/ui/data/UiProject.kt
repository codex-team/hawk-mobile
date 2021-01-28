package so.codex.hawk.ui.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.view.View
import androidx.core.content.ContextCompat
import so.codex.hawk.R
import so.codex.hawk.custom.views.SquircleDrawable
import so.codex.hawk.extensions.domain.Utils

/**
 * Ui project model for recyclerView.
 * @see so.codex.hawk.entity.Project
 */
data class UiProject(
    val id: String,
    val name: String,
    val description: String,
    val image: String = "",
    val unreadCount: Int,
    val imageDrawable: SquircleDrawable,
    val badgeModel: UiBadgeViewModel = UiBadgeViewModel()
) {
    /**
     * Abbreviated format of events ready for display.
     */
    val abbreviationEvents: String = Utils.getAbbreviationFromInt(unreadCount)
}