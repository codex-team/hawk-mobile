package so.codex.hawk.domain.login

import com.apollographql.apollo.rx3.rxMutate
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import so.codex.hawk.LoginMutation
import so.codex.hawk.SessionKeeper
import so.codex.hawk.entity.Session
import so.codex.hawk.entity.Token
import so.codex.hawk.entity.UserAuthData
import so.codex.hawk.network.NetworkProvider
import so.codex.hawk.ui.login.LoginEvent
import timber.log.Timber

class LoginInteractorImpl : LoginInteractor {
    private val publishSubject: PublishSubject<UserAuthData> = PublishSubject.create()
    override fun getLoginEventObservable(): Observable<LoginEvent> {
        val client = NetworkProvider.getApolloClient()
        return publishSubject.hide()
            .doOnSubscribe {
                Timber.i("LoginEvent subscription")
            }
            .switchMapSingle {
                client.rxMutate(LoginMutation(it.email, it.password))
            }.map {
                if (it.errors == null && it.data != null) {
                    val time = System.currentTimeMillis()
                    val accessToken = it.data!!.login.accessToken
                    val refreshToken = it.data!!.login.refreshToken
                    SessionKeeper.saveSession(Session(Token(accessToken, refreshToken), time))
                    LoginEvent.SUCCESSFUL_LOGIN
                } else {
                    for (error in it.errors!!) {
                        Timber.e(error.message)
                    }
                    LoginEvent.LOGIN_ERROR
                }
            }
            .onErrorResumeNext {
                Timber.e(it)
                getLoginEventObservable().startWithItem(LoginEvent.INTERNET_ERROR)
            }
            .subscribeOn(Schedulers.io())
    }

    override fun login(userAuthData: UserAuthData) {
        Timber.i("Attempt to login.")
        publishSubject.onNext(userAuthData)
    }
}