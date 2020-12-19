package so.codex.hawk.extensions.domain

import android.graphics.Color
import java.util.Locale

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
}
