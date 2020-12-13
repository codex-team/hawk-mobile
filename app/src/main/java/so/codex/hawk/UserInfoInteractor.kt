package so.codex.hawk

import com.apollographql.apollo.rx3.rxQuery
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import so.codex.hawk.entity.User
import so.codex.hawk.network.NetworkProvider

class UserInfoInteractor {
    fun fetchUser(): Observable<User> {
        return NetworkProvider.getApolloClient().rxQuery(UserInfoQuery())
            .doOnError {
                it.printStackTrace()
            }
            .subscribeOn(Schedulers.io())
            .map {
                val user = it!!.data!!.me!!
                User(user.id, user.name, user.email, user.image)
            }
    }
}