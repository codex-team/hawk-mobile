package so.codex.hawk.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import so.codex.hawk.HawkApp
import so.codex.hawk.domain.providers.AuthProvider
import so.codex.hawk.domain.refresh.RefreshEvent
import so.codex.hawk.domain.refresh.RefreshTokenInteractor
import so.codex.hawk.domain.splash.SplashEvent
import timber.log.Timber
import javax.inject.Inject

/**
 * The ViewModel class for SplashActivity.
 * Designed to handle various processes associated with the specified activity.
 */
class SplashViewModel : ViewModel() {
    /**
     * @property splashEvent A LiveData object to reactively pass a SplashEvent to the View.
     * @see LiveData
     */
    private val splashEvent: MutableLiveData<SplashEvent> = MutableLiveData()

    /**
     * @property refreshTokenInteractor Interactor for handling splash logic.
     */
    @Inject
    lateinit var refreshTokenInteractor: RefreshTokenInteractor

    @Inject
    lateinit var authProvider: AuthProvider

    /**
     * Initialization block. Called on creation. Check if user is authenticated and send event to
     * activity
     */
    init {
        HawkApp.mainComponent.inject(this)
        authProvider
            .isAuthorized()
            .take(1)
            .switchMap {
                if (it)
                    refreshTokenInteractor.getRefreshEventObservable()
                else {
                    Observable.just(RefreshEvent.NO_AUTHORIZED)
                }
            }.observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                splashEvent.value = it.toSplashEvent()
            }
    }

    /**
     * Method to provide LiveData [splashEvent] for observers.
     *
     * @return [LiveData] to monitor [SplashEvent].
     */
    fun observeSplashEvent(): LiveData<SplashEvent> {
        return splashEvent
    }

    /**
     * Token freshness check method.
     */
    fun checkingSessionFreshness() {
        Timber.e("#info refresh token ${Thread.currentThread().name}")
        refreshTokenInteractor.refreshToken()
    }

    /**
     * Extension method for converting token [RefreshEvent] to [SplashScreen] events.
     *
     * @return [SplashEvent]
     */
    private fun RefreshEvent.toSplashEvent(): SplashEvent {
        return when (this) {
            RefreshEvent.REFRESH_FAILED -> SplashEvent.SESSION_EXPIRED
            RefreshEvent.REFRESH_SUCCESS -> SplashEvent.SESSION_ACTIVE
            RefreshEvent.NO_INTERNET -> SplashEvent.NO_INTERNET
            RefreshEvent.NO_AUTHORIZED -> SplashEvent.SESSION_EXPIRED
        }
    }
}
