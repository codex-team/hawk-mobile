package so.codex.hawk.domain.main

import io.reactivex.rxjava3.core.Observable
import so.codex.hawk.entity.User

interface UserDataInteractor {
    fun getUserDataObservable(): Observable<User>
    fun update()
}