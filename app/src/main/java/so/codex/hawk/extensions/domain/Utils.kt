package so.codex.hawk.extensions.domain

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import androidx.core.content.ContextCompat
import java.util.Locale
import so.codex.hawk.R

/**
 * Helped variables and methods
 */

object Utils {
    /**
     * Colors for background of default logos
     */
    private val colors = arrayOf(
        "#15c46d",
        "#36a9e0",
        "#ef4b4b",
        "#4ec520",
        "#b142af",
        "#6632b8",
        "#3251b8",
        "#505b74"
    ).map {
        Color.parseColor(it)
    }

    /**
     * Get color by Id
     *
     * @param id last hex char in uuid of something
     *
     * @return Color for that uuid, if that last char is not in hex
     */
    fun getColorById(id: String): Int {
        return when (val ch = id.last()) {
            in '0'..'9' -> {
                colors[(ch - '0') / 2]
            }
            in 'a'..'f' -> {
                colors[(ch - 'a')]
            }
            else -> Color.WHITE
        }
    }

    /**
     * The method of forming an abbreviation from the name.
     *
     * Example: Android application -> AA.
     */
    fun getAbbreviationFromString(value: String): String {
        if (value.isBlank()) {
            return " "
        }
        val abb = value.split(' ').filter { it.isNotEmpty() }
        return if (abb.size == 1) {
            "${abb[0][0]}"
        } else {
            "${abb[0][0]}${abb[1][0]}"
        }.toUpperCase(Locale.ROOT)
    }

    /**
     * The method of forming an abbreviation from the Int
     *
     * Example:
     * param:   1 -> return '1' (from 1 to 999)
     *          1540 -> return '1 540' (from 1000 to 9999)
     *          11267 -> return '11.3K' (over 9999)
     */
    fun getAbbreviationFromInt(value: Int): String {
        val maxNumberWithoutAbbreviation = 9999
        return if (value <= maxNumberWithoutAbbreviation) {
            if (value < 1000) {
                value.toString()
            } else {
                val valueStr = value.toString()
                "${valueStr[0]} ${valueStr[1]}${valueStr[2]}${valueStr[3]}"
            }
        } else {
            val valueStr = value.toString()
            var remainderToThousand = value % 1000
            val remainderStr = if (remainderToThousand < 900 && remainderToThousand % 100 > 50) {
                remainderToThousand += 100
                remainderToThousand.toString()[0]
            } else {
                remainderToThousand.toString()[0]
            }
            "${valueStr.substring(0, valueStr.length - 3)}.${remainderStr}K"
        }
    }

    /**
     * Method for creating a standard icon
     */
    fun createDefaultLogo(
        context: Context,
        projectId: String,
        projectName: String,
        logoSideId: Int
    ): Bitmap {
        val side = context.resources.getDimensionPixelSize(logoSideId)
        val defaultIcon = Bitmap.createBitmap(
            side,
            side,
            Bitmap.Config.ARGB_8888
        )
        val abbreviationName = getAbbreviationFromString(projectName)
        val canvas = Canvas(defaultIcon!!)
        val fontPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            textSize = 14f * context.resources.displayMetrics.density + 0.5f
            typeface = Typeface.create("roboto", Typeface.BOLD)
            color = ContextCompat.getColor(context, R.color.colorDefaultTextIcon)
        }
        val bounds = Rect()
        fontPaint.getTextBounds(abbreviationName, 0, abbreviationName.length, bounds)
        fontPaint.textAlign = Paint.Align.LEFT
        canvas.drawColor(getColorById(projectId))
        val centerX = side / 2f - bounds.exactCenterX()
        val centerY = side.toFloat() / 2f - bounds.exactCenterY()
        canvas.drawText(abbreviationName, centerX, centerY, fontPaint)
        return defaultIcon
    }
}
