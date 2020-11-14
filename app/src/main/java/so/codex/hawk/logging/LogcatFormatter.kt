package so.codex.hawk.logging

import android.util.Log

/**
 * To use your formatter in [FileLoggingTree], you need it to implement this interface.
 */
interface LogcatFormatter {
    /**
     * The method of formatting the original message before writing it to the log.
     *
     * @param priority Log record priority. Depends on the method used when writing to the log.
     * @see Log
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class
     *            or activity where the log call occurs.
     *
     * @param message contains the message sent to the log.
     *
     * @return processed message that will be written to the log.
     */
    fun format(priority: Int, tag: String?, message: String): String
}
