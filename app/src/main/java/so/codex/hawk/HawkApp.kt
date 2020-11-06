package so.codex.hawk

import android.app.Application

/**
 * Heir to the Application class. Created by the system in a single copy when the application starts.
 * Used for one-time initialization of application components.
 */
class HawkApp : Application() {
    /**
     * Method used for one-time initialization of components.
     */
    override fun onCreate() {
        super.onCreate()
        // Initializing the class for working with the session
        SessionKeeper.init(applicationContext)
    }
}
