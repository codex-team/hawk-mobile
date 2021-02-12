package so.codex.hawk.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import so.codex.hawk.SessionKeeper
import so.codex.hawk.domain.providers.AuthProvider
import so.codex.hawk.notification.domain.NotificationManager
import javax.inject.Singleton

/**
 * Module with common dependencies, that is needed in all areas of applications
 * @property context Need for getting files, sharedPreferences and other thinks, that need
 *           Application context
 */
@Module
class AppModule(private val context: Context) {

    /**
     * Provide the application context
     * @return [Context]
     */
    @Singleton
    @Provides
    fun appContext(): Context {
        return context
    }

    /**
     * Provide Notification manager
     * @return [NotificationManager]
     */
    @Singleton
    @Provides
    fun provideNotificationManager(): NotificationManager {
        return NotificationManager()
    }

    /**
     * Provide Session keeper
     * @param context need for getting access to shared preferences
     * @return [SessionKeeper]
     */
    @Singleton
    @Provides
    fun sessionKeeper(context: Context): SessionKeeper {
        return SessionKeeper(context)
    }

    /**
     * Provide Auth provider
     * @param sessionKeeper for checking if user authorized
     * @return [AuthProvider]
     */
    @Singleton
    @Provides
    fun authProvider(sessionKeeper: SessionKeeper): AuthProvider {
        return AuthProvider(sessionKeeper)
    }
}