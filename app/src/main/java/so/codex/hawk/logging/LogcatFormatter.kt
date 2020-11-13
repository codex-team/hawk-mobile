package so.codex.hawk.logging


import android.util.Log
import java.text.SimpleDateFormat
import java.util.*


object LogcatFormatter : Formatter {
    private val prioPrefixes = HashMap<Int, String>()
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US)

    init {
        prioPrefixes.apply {
            put(Log.VERBOSE, "V/")
            put(Log.DEBUG, "D/")
            put(Log.INFO, "I/")
            put(Log.WARN, "W/")
            put(Log.ERROR, "E/")
            put(Log.ASSERT, "WTF/")
        }
    }


    override fun format(priority: Int, tag: String?, message: String): String {
        val prio = prioPrefixes[priority] ?: ""
        val time = dateFormat.format(System.currentTimeMillis())
        return "[$time] $prio${tag ?: ""}: $message\n"
    }


}