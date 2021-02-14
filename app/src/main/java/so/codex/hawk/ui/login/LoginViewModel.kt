package so.codex.hawk.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import so.codex.hawk.HawkApp
import so.codex.hawk.domain.login.LoginEvent
import so.codex.hawk.domain.login.LoginInteractor
import so.codex.hawk.entity.auth.UserAuthData
import timber.log.Timber
import javax.inject.Inject

/**
 * The ViewModel class for LoginActivity.
 * Designed to handle various processes associated with the specified activity.
 */
class LoginViewModel : ViewModel() {
    /**
     * @property loginInteractor Interactor for handling authorization logic.
     */
    @Inject
    lateinit var loginInteractor: LoginInteractor

    /**
     * @property loginEvent A LiveData object to reactively pass a LoginEvent to the View.
     * @see LiveData
     */
    private val loginEvent = MutableLiveData<LoginEvent>()

    /**
     * @property loginEventDisposable field for saving the [Disposable] object,
     *                                which is required to unsubscribe from LoginEventObservable.
     */
    private lateinit var loginEventDisposable: Disposable

    /**
     * Initialization block. Called on creation.
     */
    init {
        HawkApp.mainComponent.inject(this)
        subscribeLoginInteractor()
    }

    /**
     * Authorization method. ViewModel delegates [LoginInteractor] execution.
     *
     * @param email user email.
     *
     * @param password user password.
     */
    fun login(email: String, password: String) {
        loginInteractor.login(UserAuthData(email, password))
    }

    /**
     * Method to provide LiveData [loginEvent] for observers.
     *
     * @return [LiveData] to monitor [LoginEvent].
     */
    fun observeLoginEvent(): LiveData<LoginEvent> {
        return loginEvent
    }

    /**
     * Method for subscribing to results of execution [loginInteractor]
     */
    private fun subscribeLoginInteractor() {
        loginEventDisposable =
            loginInteractor
                .getLoginEventObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    loginEvent.value = it
                }
    }

    /**
     * The method that will be called when the activity to which the ViewModel belongs is destroyed.
     * Needed to clean up used resources.
     * @see [LoginActivity]
     */
    override fun onCleared() {
        loginEventDisposable.dispose()
        Timber.i("LoginEvent dispose!")
        super.onCleared()
    }
}
