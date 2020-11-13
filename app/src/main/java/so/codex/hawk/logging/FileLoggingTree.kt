package so.codex.hawk.logging

import android.util.Log
import java.io.File
import java.util.logging.FileHandler
import java.util.logging.Formatter
import java.util.logging.Level
import java.util.logging.LogRecord
import java.util.logging.Logger
import so.codex.hawk.AppData
import so.codex.hawk.logging.FileLoggingTree.Companion.DEFAULT_LOG_NAME
import so.codex.hawk.logging.FileLoggingTree.Companion.LOGS_SUBFOLDER
import timber.log.Timber

/**
 * Tree class with implemented logging to a file.
 * To use, you need to install an instance in [Timber].
 *
 * @property logPath Path for saving logs. A subfolder [LOGS_SUBFOLDER] is created
 *                   at the specified path in which logs will be saved.
 *
 * @property logName Used to name log files.
 * @see [DEFAULT_LOG_NAME]
 *
 * @property formatter Accepts an instance that implements the [so.codex.hawk.logging.Formatter] interface.
 *                    Used to format the output of messages to a file.
 *
 * @property logger contains an instance of the [Logger] class.
 *
 * @property NO_FORMATTER contains a stub to ignore the formatter from the [FileHandler].
 *
 * @param sizeLimit Accepts the maximum size in bytes for a single log file. [AppData.SIZE_LOG_FILE]
 *                  The default is set to the value from the AppData.SIZE_LOG_FILE.
 *
 * @param fileLimit Accepts the maximum number for log files. [AppData.LIMIT_LOG_FILE]
 *                  The default is set to the value from the AppData.LIMIT_LOG_FILE.
 */
class FileLoggingTree(
    private val logPath: String,
    private val logName: String = DEFAULT_LOG_NAME,
    private val formatter: so.codex.hawk.logging.Formatter,
    sizeLimit: Int = AppData.SIZE_LOG_FILE,
    fileLimit: Int = AppData.LIMIT_LOG_FILE
) : Timber.DebugTree() {
    private val logger = Logger.getLogger(LOGGER_NAME)
    private val NO_FORMATTER = object : Formatter() {
        override fun format(record: LogRecord?) = record?.message ?: ""
    }

    /**
     * @property LOGGER_NAME Name to receive the logger.
     *
     * @property LOGS_SUBFOLDER contains the directory that will be created along the path
     *                          passed to logPath. Log files will be stored in this directory.
     *
     * @property DEFAULT_LOG_NAME contains the default name for log files. The log files will
     *                            be named as follows: log0, log1 and so on.
     *
     */
    companion object {
        private const val LOGGER_NAME = "HAWK_LOGGER"
        private const val LOGS_SUBFOLDER = "/logs"
        private const val DEFAULT_LOG_NAME = "log"
    }

    /**
     * Instance initialization block. Sets the necessary settings for correct operation.
     */
    init {
        val logPattern = createLogPattern()
        val fileHandler = FileHandler(logPattern, sizeLimit, fileLimit, true)
        fileHandler.formatter = NO_FORMATTER
        logger.level = Level.ALL
        logger.addHandler(fileHandler)
    }

    /**
     * A method to be called every time a message is posted to the log, regardless of the level.
     * Passes a call to the parent to write a log to the console, and then logs to a file.
     *
     * @param priority Log record priority. Depends on the method used when writing to the log.
     * @see Log
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class
     *            or activity where the log call occurs.
     *
     * @param message contains the message sent to the log.
     *
     * @t Contains an instance describing the error that occurred.
     *
     */
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        super.log(priority, tag, message, t)
        if (t == null) {
            logger.log(
                fromPriorityToLevel(priority),
                formatter.format(priority, tag, message)
            )
        } else {
            logger.log(fromPriorityToLevel(priority), "", t)
        }
    }

    /**
     * Method for converting priority constants to level constants.
     * @see Log
     * @see Level
     *
     * @param priority The priority to be converted to level.
     *
     * @return one of the [Level]
     */
    private fun fromPriorityToLevel(priority: Int): Level {
        return when (priority) {
            Log.VERBOSE -> Level.FINER
            Log.DEBUG -> Level.FINE
            Log.INFO -> Level.INFO
            Log.WARN -> Level.WARNING
            Log.ERROR, Log.ASSERT -> Level.SEVERE
            else -> Level.FINEST
        }
    }

    /**
     * Method of forming a pattern for saving a log.
     *
     * @return pattern (path) based on which log records will be saved.
     */
    private fun createLogPattern(): String {
        val logDirectory = "$logPath/$LOGS_SUBFOLDER"
        val file = File(logDirectory)
        if (!file.isDirectory) file.mkdir()
        return "$logDirectory/$logName"
    }
}
