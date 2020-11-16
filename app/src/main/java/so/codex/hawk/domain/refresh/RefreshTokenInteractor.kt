package so.codex.hawk.domain.refresh

import io.reactivex.rxjava3.core.Observable

/**
 * Interface for Interactor providing tokens refresh.
 */
interface RefreshTokenInteractor {
    /**
     * @return Observable that allows you to monitor the [RefreshEvent] that occurs
     *         when trying to refresh tokens.
     */
    fun getRefreshEventObservable(): Observable<RefreshEvent>

    /**
     * Token refresh method
     */
    fun refreshToken()
}
