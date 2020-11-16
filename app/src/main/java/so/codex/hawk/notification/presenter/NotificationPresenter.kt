package so.codex.hawk.notification.presenter

import io.reactivex.rxjava3.disposables.Disposable

/**
 * Interface that contain methods for working with view or container
 */
interface NotificationPresenter {
    /**
     * Subscribe for getting new information about notification
     * @return disposable to release resources
     */
    fun subscribe(): Disposable
}