package so.codex.hawk

import android.content.Context
import android.content.SharedPreferences
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import so.codex.hawk.entity.Session
import so.codex.hawk.entity.Token
import timber.log.Timber

/**
 * This class (singleton) is responsible for saving session data (accessToken,refreshToken, time).
 */
object SessionKeeper {
    /**
     * @property context An instance of the Context class. Used to access [SharedPreferences].
     */
    private lateinit var context: Context

    /**
     * @property KEY_SESSION_PREF Key to get sharedPreferences from context.
     */
    private const val KEY_SESSION_PREF = "SESSION_PREF"

    /**
     * @property KEY_ACCESS_TOKEN The key to get the access token value stored on the device.
     */
    private const val KEY_ACCESS_TOKEN = "ACCESS_TOKEN"

    /**
     * @property KEY_REFRESH_TOKEN The key to get the refresh token value stored on the device.
     */
    private const val KEY_REFRESH_TOKEN = "REFRESH_TOKEN"

    /**
     * @property KEY_TIME_SESSION A key for getting the value of the start time of
     *                            the last session stored on the device.
     */
    private const val KEY_TIME_SESSION = "TIME_SESSION"

    /**
     * @property EMPTY_SESSION A stub session instance in the absence of a valid session.
     */
    private val EMPTY_SESSION = Session(Token("", ""), 0)

    /**
     * @property session Field for storing the [Session] instance while the application is running.
     *                   The field is open for reading and closed for direct installation. To save
     *                   a new instance of the session, use the .saveSession(newSession) methods.
     */
    var session: Session = EMPTY_SESSION
        private set

    /**
     * @property sessionSubject An instance of BehaviorSubject extending the Observable class and
     *                       capable of sending updates to subscribers. When subscribed, sends
     *                       the last session update. This instance sends a token containing
     *                       new session data.
     */
    private val sessionSubject: BehaviorSubject<Session> = BehaviorSubject.create()

    /**
     * [SessionKeeper] initialization method.
     * Must be called 1 time at application start (In [HawkApp] class).
     *
     * @param context Must be an applicationContext to avoid memory leaks.
     */
    fun init(context: Context) {
        if (!this::context.isInitialized) {
            Timber.i("SessionKeeper initialization.")
            this.context = context
            val preferences = context.getSharedPreferences(KEY_SESSION_PREF, Context.MODE_PRIVATE)
            restoreSessionFromPref(preferences)
            sessionSubject.onNext(session)
        }
    }

    /**
     * Method for updating a session instance while the application is running.
     * The method also stores information about the new session in sharedPreferences.
     *
     * @param newSession An instance of the new session. Must be populated with [Token]
     *                   and last update time.
     */
    fun saveSession(newSession: Session) {
        Timber.i("Saving a new session.")
        session = newSession
        saveToSharedPref(session)
        sessionSubject.onNext(session)
    }

    /**
     * Method of providing [Observable] for token update subscription capabilities.
     *
     * @return [Observable] <[Token]> when subscribing to which it will be possible
     *                                                 to handle the token change.
     */
    fun getTokenObservable(): Observable<Token> {
        return sessionSubject.hide().map { it.token }
    }

    /**
     * Method of providing [Observable] for session update subscription capabilities.
     *
     * @return [Observable] <[Session]> when subscribing to which it will be possible
     *                                                 to handle the session change.
     */
    fun getSessionObservable(): Observable<Session> {
        return sessionSubject.hide()
    }

    /**
     * Session recovery method from sharedPreferences saved on the device.
     *
     * @param preferences sharedPreferences saved on the device.
     * @see init
     */
    private fun restoreSessionFromPref(preferences: SharedPreferences) {
        val accessToken = preferences.getString(KEY_ACCESS_TOKEN, "")!!
        val refreshToken = preferences.getString(KEY_REFRESH_TOKEN, "")!!
        val time = preferences.getLong(KEY_TIME_SESSION, 0)
        if (accessToken.isBlank() or refreshToken.isBlank()) return
        session = Session(Token(accessToken, refreshToken), time)
        Timber.i("Session recovery from SharedPreferences.")
    }

    /**
     * The method of saving the session to sharedPreferences stored on the device.
     *
     * @param session Session instance containing information about the new user session.
     */
    private fun saveToSharedPref(session: Session) {
        val editor = context.getSharedPreferences(KEY_SESSION_PREF, Context.MODE_PRIVATE).edit()
        editor.putString(KEY_ACCESS_TOKEN, session.token.accessToken)
        editor.putString(KEY_REFRESH_TOKEN, session.token.refreshToken)
        editor.putLong(KEY_TIME_SESSION, session.time)
        editor.apply()
        Timber.i("Successful saving of session in SharedPreferences!")
    }
}
