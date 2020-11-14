package so.codex.hawk.logging

import android.util.Log
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * A class that implements [LogcatFormatter].
 * It is designed to format the original message before being written to the log.
 */
class LogcatFormatterImpl : LogcatFormatter {
    /**
     * @property priorityPrefixes contains prefixes to indicate the type of log record.
     */
    private val priorityPrefixes = HashMap<Int, String>()

    /**
     * @property dateFormat [SimpleDateFormat] object to convert time from [Long] to format
     *                      according to the pattern.
     */
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US)

    /**
     * Initialization block. Prefixes are set for each log type.
     */
    init {
        priorityPrefixes.apply {
            put(Log.VERBOSE, "V/")
            put(Log.DEBUG, "D/")
            put(Log.INFO, "I/")
            put(Log.WARN, "W/")
            put(Log.ERROR, "E/")
            put(Log.ASSERT, "WTF/")
        }
    }

    /**
     * The method of formatting the original message before writing it to the log.
     * @see LogcatFormatter.format
     */
    override fun format(priority: Int, tag: String?, message: String): String {
        val prio = priorityPrefixes[priority] ?: ""
        val time = dateFormat.format(System.currentTimeMillis())
        return "[$time] $prio${tag ?: ""}: $message\n"
    }
}
