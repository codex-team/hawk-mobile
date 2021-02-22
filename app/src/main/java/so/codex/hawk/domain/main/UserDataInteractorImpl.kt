package so.codex.hawk.domain.main

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.rx3.rxQuery
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import so.codex.hawk.UserDataQuery
import so.codex.hawk.entity.User
import timber.log.Timber

/**
 * Interactor for getting user data from api.
 *
 * @param client [ApolloClient]
 */
class UserDataInteractorImpl(private val client: ApolloClient) : UserDataInteractor {
    /**
     * Subject to update user data.
     */
    private val innerDataSubject = BehaviorSubject.create<Unit>()

    /**
     * Subject for getting observable [User]
     */
    private val userDataSubject = BehaviorSubject.create<User>()

    init {
        innerDataSubject.hide()
            .subscribeOn(Schedulers.io())
            .switchMap {
                client.rxQuery(UserDataQuery())
            }
            .distinctUntilChanged()
            .map {
                it.data?.me?.toUser() ?: User()
            }
            .subscribe(
                {
                    userDataSubject.onNext(it)
                },
                {
                    Timber.e(it)
                }
            )
        innerDataSubject.onNext(Unit)
    }

    /**
     * Method getting observable user.
     */
    override fun getUserDataObservable(): Observable<User> {
        return userDataSubject.hide()
    }

    /**
     * Method for update user data.
     */
    override fun update() {
        innerDataSubject.onNext(Unit)
    }

    private fun UserDataQuery.Me.toUser(): User {
        return User(
            id,
            email ?: "",
            name ?: "",
            image ?: ""
        )
    }
}