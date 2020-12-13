package so.codex.hawk.domain.login

import io.reactivex.rxjava3.core.Observable
import so.codex.hawk.entity.auth.UserAuthData

/**
 * An interface for interactor that will perform authorization.
 */
interface LoginInteractor {
    /**
     * Observable getter for monitoring authorization events.
     *
     * @return an Observable object, subscribing to which after calling
     * @see login method, you can get the result in the form of [LoginEvent].
     */
    fun getLoginEventObservable(): Observable<LoginEvent>

    /**
     * The method for authorizing the user.
     *
     * @param userAuthData user authorization data model. Contains email and password.
     */
    fun login(userAuthData: UserAuthData)
}
