package so.codex.hawk.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import so.codex.hawk.domain.refresh.RefreshTokenInteractor
import so.codex.hawk.domain.refresh.RefreshTokenInteractorImpl
import so.codex.hawk.domain.splash.SplashEvent
import so.codex.hawk.extensions.domain.toSplashEvent
import timber.log.Timber

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
    private val refreshTokenInteractor: RefreshTokenInteractor = RefreshTokenInteractorImpl()

    /**
     * Initialization block. Called on creation.
     */
    init {
        refreshTokenInteractor.getRefreshEventObservable()
            .observeOn(AndroidSchedulers.mainThread())
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
}
