package so.codex.hawk

import android.app.Application
import so.codex.hawk.di.DaggerMainComponent
import so.codex.hawk.di.MainComponent
import so.codex.hawk.di.modules.AppModule
import so.codex.hawk.logging.FileLoggingTree
import so.codex.hawk.logging.LogcatFormatterImpl
import timber.log.Timber

/**
 * Heir to the Application class. Created by the system in a single copy when the application starts.
 * Used for one-time initialization of application components.
 */
class HawkApp : Application() {

    companion object {
        /**
         * Component that have dependencies graph
         */
        lateinit var mainComponent: MainComponent
            private set
    }

    /**
     * Method used for one-time initialization of components.
     */
    override fun onCreate() {
        super.onCreate()
        mainComponent = DaggerMainComponent.builder()
            .appModule(AppModule(applicationContext))
            .build()
        // Initializing the class for logging
        Timber.plant(FileLoggingTree(applicationInfo.dataDir, formatter = LogcatFormatterImpl()))
    }
}
