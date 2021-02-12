package so.codex.hawk.domain.refresh

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.rx3.rxMutate
import com.jakewharton.rxrelay3.PublishRelay
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import so.codex.hawk.RefreshMutation
import so.codex.hawk.SessionKeeper
import so.codex.hawk.entity.auth.Session
import so.codex.hawk.entity.auth.Token
import so.codex.hawk.extensions.apollo.toRefreshResponse
import timber.log.Timber

/**
 * Class that implements the RefreshTokenInteractor interface.
 * Allows you to make a request to the API to update tokens.
 */
class RefreshTokenInteractorImpl(
    private val client: ApolloClient,
    private val sessionKeeper: SessionKeeper
) : RefreshTokenInteractor {
    /**
     * @property publishRelay Subject allowing emitting items to subscribers.
     *                          Works according to the Observer pattern.
     * @see PublishRelay
     */
    private val publishRelay: PublishRelay<Token> = PublishRelay.create()

    /**
     * @property refreshEventSubject Subject allowing emitting items to subscribers.
     *                          Works according to the Observer pattern.
     * @see PublishSubject
     */
    private val refreshEventSubject = BehaviorSubject.create<RefreshEvent>()

    /**
     * Subscribe on event, if need to refresh token and send request
     */
    init {
        publishRelay
            .distinctUntilChanged()
            .subscribeOn(Schedulers.io())
            .switchMapSingle {
                Timber.i("Switch publish subject to RefreshObservable")
                client.rxMutate(RefreshMutation(it.refreshToken))
            }
            .map {
                Timber.i("Received a response from the server.")
                val response = it.toRefreshResponse()
                if (!response.hasError) {
                    val time = System.currentTimeMillis()
                    sessionKeeper.saveSession(Session(response.token, time))
                    RefreshEvent.REFRESH_SUCCESS
                } else {
                    Timber.i("Token refresh failed. Token is invalidate.")
                    RefreshEvent.REFRESH_FAILED
                }
            }
            .onErrorResumeNext {
                Timber.e("The request was aborted. Network error.")
                Observable.just(RefreshEvent.NO_INTERNET)
            }
            .subscribe {
                refreshEventSubject.onNext(it)
            }
    }

    /**
     * @return Observable that allows you to monitor the [RefreshEvent] that occurs
     *         when trying to refresh tokens.
     */
    override fun getRefreshEventObservable(): Observable<RefreshEvent> {
        Timber.i("Creating a new instance of ObservableRefreshEvent.")
        return refreshEventSubject
    }

    /**
     * Token refresh method
     */
    override fun refreshToken() {
        Timber.i("Attempting to refresh a token ${Thread.currentThread().name}")
        publishRelay.accept(sessionKeeper.session.token)
    }
}
