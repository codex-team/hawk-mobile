package so.codex.hawk

import android.app.Application
import android.content.Context
import so.codex.hawk.logging.FileLoggingTree
import so.codex.hawk.logging.LogcatFormatterImpl
import timber.log.Timber

/**
 * Heir to the Application class. Created by the system in a single copy when the application starts.
 * Used for one-time initialization of application components.
 */
class HawkApp : Application() {

    companion object {
        private lateinit var context: Context
        fun getContext(): Context {
            return context
        }
    }

    /**
     * Method used for one-time initialization of components.
     */
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        // Initializing the class for working with the session
        SessionKeeper.init(applicationContext)
        // Initializing the class for logging
        Timber.plant(FileLoggingTree(applicationInfo.dataDir, formatter = LogcatFormatterImpl()))
    }
}
