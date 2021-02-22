package so.codex.hawk.ui.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.squareup.picasso.Picasso
import dagger.Lazy
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.Observables
import io.reactivex.rxjava3.schedulers.Schedulers
import so.codex.hawk.HawkApp
import so.codex.hawk.R
import so.codex.hawk.custom.views.SquircleDrawable
import so.codex.hawk.domain.FetchWorkspacesInteractor
import so.codex.hawk.domain.main.UserDataInteractor
import so.codex.hawk.domain.providers.ExternalSourceWorkspace
import so.codex.hawk.entity.User
import so.codex.hawk.entity.WorkspaceCut
import so.codex.hawk.extensions.domain.Utils
import so.codex.hawk.ui.data.UiUser
import so.codex.hawk.ui.data.UiWorkspace
import timber.log.Timber
import javax.inject.Inject

class DrawerViewModel : ViewModel() {

    /**
     * @see Context
     */
    @Inject
    lateinit var context: Context

    /**
     * A LiveData with list of [UiWorkspace] that should be inserted to the view
     */
    private val uiWorkspace: MutableLiveData<List<UiWorkspace>> = MutableLiveData()

    /**
     *
     */
    private val uiUserData: MutableLiveData<UiUser> = MutableLiveData()

    /**
     * @property fetchWorkspaceInteractor for getting workspaces
     */
    @Inject
    lateinit var fetchWorkspaceInteractor: FetchWorkspacesInteractor

    /**
     * @property userDataInteractor for getting user data.
     */
    @Inject
    lateinit var userDataInteractor: Lazy<UserDataInteractor>

    /**
     * @property externalSourceWorkspace for transmitting information about the selected workspace.
     */
    @Inject
    lateinit var externalSourceWorkspace: ExternalSourceWorkspace

    private var disposable = CompositeDisposable()

    init {
        HawkApp.mainComponent.inject(this)
        disposable.addAll(
            subscribeOnWorkspaces(),
            subscribeOnUserData()
        )
        fetchWorkspaceInteractor.update()
    }

    fun observeUiWorkspace(): LiveData<List<UiWorkspace>> {
        return uiWorkspace
    }

    fun observeUiUserData(): LiveData<UiUser> {
        return uiUserData
    }

    private fun subscribeOnWorkspaces(): Disposable {
        return Observables.combineLatest(
            fetchWorkspaceInteractor.fetchWorkspaces()
                .subscribeOn(Schedulers.io()),
            externalSourceWorkspace.selectedWorkspaceObserve()
                .observeOn(Schedulers.io())
        )
            .subscribeOn(Schedulers.io())
            .map { (workspaceList, selectedWorkspace) ->
                workspaceList.map { workspace ->
                    workspace.toUiWorkspace(workspace.id == selectedWorkspace.id)
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    uiWorkspace.value = it
                },
                {
                    Timber.e(it)
                }
            )
    }

    private fun subscribeOnUserData(): Disposable {
        return userDataInteractor
            .get()
            .getUserDataObservable()
            .map {
                it.toUiUser()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    uiUserData.value = it
                },
                {
                    Timber.e(it)
                }
            )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

    private fun WorkspaceCut.toUiWorkspace(isSelected: Boolean): UiWorkspace {
        return UiWorkspace(
            id,
            name,
            SquircleDrawable(
                if (image.isBlank()) {
                    Utils.createDefaultLogo(
                        context,
                        id,
                        name,
                        R.dimen.workspace_image_side
                    )
                } else {
                    Picasso.get().load(image).get()
                }
            ),
            isSelected,
            onClick = {
                externalSourceWorkspace.setSelectedWorkspace(this)
            }
        )
    }

    private fun User.toUiUser(): UiUser {
        return UiUser(
            name = if (name.isEmpty()) email else name,
            SquircleDrawable(
                if (image.isBlank()) {
                    Utils.createDefaultLogo(context, id, name, R.dimen.user_image_side)
                } else {
                    Picasso.get().load(image).get()
                }
            )
        )
    }
}