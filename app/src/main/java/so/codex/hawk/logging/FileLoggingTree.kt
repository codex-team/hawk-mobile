package so.codex.hawk.logging

import android.util.Log
import so.codex.hawk.AppData
import timber.log.Timber
import java.io.File
import java.util.logging.FileHandler
import java.util.logging.Formatter
import java.util.logging.Level
import java.util.logging.LogRecord
import java.util.logging.Logger


class FileLoggingTree(
    private val logPath: String,
    private val logName: String = DEFAULT_LOG_NAME,
    sizeLimit: Int = AppData.SIZE_LOG_FILE,
    fileLimit: Int = AppData.LIMIT_LOG_FILE,
    private val formatter: so.codex.hawk.logging.Formatter
) : Timber.DebugTree() {
    companion object {
        private const val LOGGER_NAME = "HAWK_LOGGER"
        private const val LOGS_SUBFOLDER = "/logs"
        private const val DEFAULT_LOG_NAME = "log"
    }

    private val logger = Logger.getLogger(LOGGER_NAME)
    private val NO_FORMATTER = object : Formatter() {
        override fun format(record: LogRecord?) = record?.message ?: ""
    }

    init {
        val logPattern = createLogPattern()
        logger.level = Level.ALL
        val fileHander = FileHandler(logPattern, sizeLimit, fileLimit, true)
        logger.addHandler(fileHander)
        fileHander.formatter = NO_FORMATTER
    }

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

    private fun createLogPattern(): String {
        val logDirectory = "$logPath/$LOGS_SUBFOLDER"
        val file = File(logDirectory)
        if (!file.isDirectory) file.mkdir()
        return "$logDirectory/$logName"
    }
}

