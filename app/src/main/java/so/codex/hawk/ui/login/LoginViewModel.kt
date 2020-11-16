package so.codex.hawk.ui.login


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import so.codex.hawk.domain.login.LoginInteractor
import so.codex.hawk.domain.login.LoginInteractorImpl
import so.codex.hawk.entity.UserAuthData
import timber.log.Timber

class LoginViewModel : ViewModel() {
    private val loginInteractor: LoginInteractor = LoginInteractorImpl()
    private val loginEvent = MutableLiveData<LoginEvent>()
    private lateinit var loginEventDisposable: Disposable


    init {
        subscribeLoginInteractor()
    }


    fun login(email: String, password: String) {
        loginInteractor.login(UserAuthData(email, password))
    }

    fun observeLoginEvent(): LiveData<LoginEvent> {
        return loginEvent
    }

    private fun subscribeLoginInteractor() {
        loginEventDisposable =
            loginInteractor.getLoginEventObservable().observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    loginEvent.value = it
                }
    }

    override fun onCleared() {
        loginEventDisposable.dispose()
        Timber.i("LoginEvent dispose!")
        super.onCleared()
    }
}