package so.codex.hawk.domain.refresh

import com.apollographql.apollo.rx3.rxMutate
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import so.codex.hawk.RefreshMutation
import so.codex.hawk.SessionKeeper
import so.codex.hawk.entity.auth.Session
import so.codex.hawk.entity.auth.Token
import so.codex.hawk.extensions.apollo.toRefreshResponse
import so.codex.hawk.network.NetworkProvider
import timber.log.Timber

/**
 * Class that implements the RefreshTokenInteractor interface.
 * Allows you to make a request to the API to update tokens.
 */
class RefreshTokenInteractorImpl : RefreshTokenInteractor {
    /**
     * @property publishSubject Subject allowing emitting items to subscribers.
     *                          Works according to the Observer pattern.
     * @see PublishSubject
     */
    private val publishSubject: PublishSubject<Token> = PublishSubject.create()

    /**
     * @return Observable that allows you to monitor the [RefreshEvent] that occurs
     *         when trying to refresh tokens.
     */
    override fun getRefreshEventObservable(): Observable<RefreshEvent> {
        Timber.i("Creating a new instance of ObservableRefreshEvent.")
        val client = NetworkProvider.getApolloClient()
        return publishSubject.hide()
            .doOnSubscribe {
                Timber.i("RefreshObservable subscription.")
            }
            .subscribeOn(Schedulers.io())
            .distinctUntilChanged()
            .switchMapSingle {
                Timber.i("Switch publish subject to RefreshObservable")
                client.rxMutate(RefreshMutation(it.refreshToken))
            }
            .map {
                Timber.i("Received a response from the server.")
                val response = it.toRefreshResponse()
                if (!response.hasError) {
                    val time = System.currentTimeMillis()
                    SessionKeeper.saveSession(Session(response.token, time))
                    RefreshEvent.REFRESH_SUCCESS
                } else {
                    Timber.i("Token refresh failed. Token is deprecated.")
                    RefreshEvent.REFRESH_FAILED
                }
            }
            .onErrorResumeNext {
                Timber.e("The request was aborted. Network error.")
                getRefreshEventObservable().startWithItem(RefreshEvent.NO_INTERNET)
            }
    }

    /**
     * Token refresh method
     */
    override fun refreshToken() {
        Timber.i("Attempting to refresh a token")
        publishSubject.onNext(SessionKeeper.session.token)
    }
}
