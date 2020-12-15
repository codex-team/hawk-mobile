package so.codex.hawk

import com.apollographql.apollo.rx3.rxQuery
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import so.codex.hawk.entity.User
import so.codex.hawk.network.NetworkProvider

/**
 * Class for getting info about user profile
 */
class UserInfoInteractor {
    /**
     * Method which fetch info about [User]
     * @return [User] wrapped in [Observable]
     * @see [Observable]
     */
    fun fetchUser(): Observable<User> {
        return NetworkProvider.getApolloClient().rxQuery(UserInfoQuery())
            .doOnError {
                it.printStackTrace()
            }
            .subscribeOn(Schedulers.io())
            .map {
                it.data?.me?.let { u ->
                    User(u.id, u.name, u.email, u.image)
                }

            }
    }
}