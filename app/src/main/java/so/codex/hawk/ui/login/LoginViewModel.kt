package so.codex.hawk.ui.login


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import so.codex.hawk.domain.login.LoginInteractor
import so.codex.hawk.domain.login.LoginInteractorImpl
import timber.log.Timber

class LoginViewModel : ViewModel() {
    private val loginInteractor: LoginInteractor = LoginInteractorImpl()
    private val loginEvent = MutableLiveData<LoginEvent>()
    private lateinit var loginEventDisposable: Disposable


    init {
        subscribeLoginInteractor()
    }


    fun login(email: String, password: String) {
        loginInteractor.login(email, password)
    }

    fun observeLoginEvent(): LiveData<LoginEvent> {
        return loginEvent
    }

    private fun subscribeLoginInteractor() {

        loginInteractor.getLoginEventObservable().observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<LoginEvent> {
                override fun onSubscribe(d: Disposable?) {
                    loginEventDisposable = d!!
                }

                override fun onNext(t: LoginEvent?) {
                    loginEvent.value = t
                }

                override fun onError(e: Throwable?) {
                    Timber.e(e)
                }

                override fun onComplete() {}
            })
    }

    override fun onCleared() {
        loginEventDisposable.dispose()
        super.onCleared()
    }
}