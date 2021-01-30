package so.codex.hawk.utils

import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.roundToLong

/**
 * Class that provide methods for work with number
 */
object ShortNumberUtils {

    /**
     * Constant with suffixes for number, used for short writing of a number
     */
    private val SUFFIXES = mapOf(
        "K" to 6,
        "M" to 9,
        "B" to 12,
        "T" to 16
    )

    /**
     * Function for converting from Long to short number with suffix
     * @param number Base number for converting
     * @return Returns the short spelling of a number
     * @sample number if you pass number 3000 in function, then returned short number is 3k
     */
    fun convert(number: Long): String {
        if (abs(number) < 1000) {
            return number.toString()
        }

        val num = abs(number)
        val sign = if (number < 0) "-" else ""
        val size = number.toString().length

        val exp = if (size % 3 == 0) size - 3 else size - (size % 3)
        val suffix = getSuffixByExp(exp)
        val shortNum = (10 * (num / 10.0.pow(exp.toDouble()))).roundToLong() / 10
        return sign + shortNum + suffix
    }

    /**
     * Get suffix by exponent of number
     * @param exp Exponent of number
     * @return Suffix for short number
     */
    private fun getSuffixByExp(exp: Int): String {
        for ((key, value) in SUFFIXES) {
            if (exp < value) {
                return key
            }
        }
        return ""
    }
}