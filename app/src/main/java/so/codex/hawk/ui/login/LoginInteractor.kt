package so.codex.hawk.ui.login

import io.reactivex.rxjava3.core.Observable

interface LoginInteractor {
    fun getLoginEventObservable(): Observable<LoginEvent>
    fun login(email: String, password: String)
}