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
import so.codex.hawk.custom.views.badge.UiBadgeViewModel
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
    val badgeModel: UiBadgeViewModel = UiBadgeViewModel()
) {

    /**
     * Abbreviated format of name ready for display.
     */
    val abbreviationName by lazy {
        Utils.getAbbreviationFromString(name)
    }

    /**
     * The default color for the icon if no custom image is set.
     */
    private val defaultColor by lazy {
        Utils.getColorById(id)
    }

    /**
     * The default icon for the icon if no custom image is set.
     */
    private var defaultIcon: Bitmap? = null

    /**
     * Method for obtaining a standard icon.
     */
    fun getDefaultIcon(view: View): Bitmap {
        if (defaultIcon == null) {
            createDefaultLogo(view.context)
        }
        return defaultIcon!!
    }

    /**
     * Method for creating a standard icon
     */
    fun createDefaultLogo(context: Context) {
        val side = context.resources.getDimensionPixelSize(R.dimen.project_icon_side)
        val defaultIcon = Bitmap.createBitmap(
            side,
            side,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(defaultIcon!!)
        val fontPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            textSize = 14f * context.resources.displayMetrics.density + 0.5f
            typeface = Typeface.create("roboto", Typeface.BOLD)
            color = ContextCompat.getColor(context, R.color.colorDefaultTextIcon)
        }
        val bounds = Rect()
        fontPaint.getTextBounds(abbreviationName, 0, abbreviationName.length, bounds)
        fontPaint.textAlign = Paint.Align.LEFT
        canvas.drawColor(defaultColor)
        val centerX = side / 2f - bounds.exactCenterX()
        val centerY = side.toFloat() / 2f - bounds.exactCenterY()
        canvas.drawText(abbreviationName, centerX, centerY, fontPaint)
        this.defaultIcon = defaultIcon
    }
}