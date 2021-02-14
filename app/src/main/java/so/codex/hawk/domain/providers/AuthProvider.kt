package so.codex.hawk.domain.providers

import io.reactivex.rxjava3.core.Observable
import so.codex.hawk.SessionKeeper

/**
 * Provide information about user authorization
 */
class AuthProvider(private val sessionKeeper: SessionKeeper) {

    /**
     * Check in storage if we have session or valid access token
     * @return Observable with boolean value if user authorized
     */
    fun isAuthorized(): Observable<Boolean> {
        return sessionKeeper
            .getSessionObservable()
            .map { session ->
                session === SessionKeeper.EMPTY_SESSION ||
                    session.token === SessionKeeper.EMPTY_TOKEN ||
                    session.token.accessToken == ""
            }
            .map { !it }
    }
}