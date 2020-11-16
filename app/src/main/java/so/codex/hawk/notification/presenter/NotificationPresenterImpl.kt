package so.codex.hawk.notification.presenter

import io.reactivex.rxjava3.disposables.Disposable
import so.codex.hawk.notification.domain.NotificationProvider

/**
 * Delegate for communication with container and invoke method for show notification
 */
class NotificationPresenterImpl(
    /**
     * @property container Notification container for showing notification
     */
    private val container: NotificationView,
    /**
     * @property provider Provide notification to display on host
     */
    private val provider: NotificationProvider
) : NotificationPresenter {

    /**
     * Subscribe and handle new notification before showing on host
     * @return disposable to release resources
     */
    override fun subscribe(): Disposable {
        return provider.observeNotification()
            .subscribe {
                container.show(it)
            }
    }
}