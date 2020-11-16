package so.codex.hawk.domain.login

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.rx3.rxMutate
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.SingleSource
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import so.codex.hawk.LoginMutation
import so.codex.hawk.SessionKeeper
import so.codex.hawk.entity.Session
import so.codex.hawk.entity.Token
import so.codex.hawk.network.NetworkProvider
import so.codex.hawk.ui.login.LoginEvent
import timber.log.Timber

class LoginInteractorImpl : LoginInteractor {
    private val publishSubject: PublishSubject<LoginEvent> = PublishSubject.create()
    private var inProgress = false


    override fun getLoginEventObservable(): Observable<LoginEvent> {
        return publishSubject.hide()
    }

    override fun login(email: String, password: String) {
        if (!inProgress) {
            inProgress = true
            val client = NetworkProvider.getApolloClient()
            val loginMutation = LoginMutation(email, password)
            val loginTime = System.currentTimeMillis()
            client.mutate(loginMutation)
                .enqueue(object : ApolloCall.Callback<LoginMutation.Data>() {
                    override fun onResponse(response: Response<LoginMutation.Data>) {
                        if (response.errors != null && response.errors!!.isNotEmpty()) {
                            publishSubject.onNext(LoginEvent.LOGIN_ERROR)
                            for (error in response.errors!!) {
                                Timber.e(error.message)
                            }
                        } else if (response.data != null) {
                            val accessToken = response.data!!.login.accessToken
                            val refreshToken = response.data!!.login.refreshToken
                            SessionKeeper.saveSession(
                                Session(
                                    Token(accessToken, refreshToken),
                                    loginTime
                                )
                            )
                            Timber.i("Successful login!")
                            publishSubject.onNext(LoginEvent.SUCCESSFUL_LOGIN)
                        }
                        inProgress = false
                    }

                    override fun onFailure(e: ApolloException) {
                        publishSubject.onNext(LoginEvent.APOLLO_ERROR)
                        Timber.e(e)
                        inProgress = false
                    }
                })
        }
    }
}