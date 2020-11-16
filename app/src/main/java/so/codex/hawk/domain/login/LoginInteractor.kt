package so.codex.hawk.domain.login

import io.reactivex.rxjava3.core.Observable
import so.codex.hawk.entity.UserAuthData
import so.codex.hawk.ui.login.LoginEvent

interface LoginInteractor {
    fun getLoginEventObservable(): Observable<LoginEvent>
    fun login(userAuthData: UserAuthData)
}