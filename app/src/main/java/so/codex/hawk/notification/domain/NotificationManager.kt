package so.codex.hawk.notification.domain

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import so.codex.hawk.notification.model.NotificationModel
import timber.log.Timber

/**
 * The manager for showing notification on host, if host attached notification container
 */
class NotificationManager : NotificationProvider {
    /**
     * @property notificationSubject subject to display notification
     */
    private val notificationSubject = PublishSubject.create<NotificationModel>()

    /**
     * Show notification on host.
     * Send in a stream notification that should be displayed on the host
     */
    fun showNotification(model: NotificationModel) {
        Timber.i("show notification ${model.text}")
        notificationSubject.onNext(model)
    }

    /**
     * Stream of notification that need to display on host
     * @return Stream of notification
     */
    override fun observeNotification(): Observable<NotificationModel> {
        return notificationSubject.hide()
    }
}