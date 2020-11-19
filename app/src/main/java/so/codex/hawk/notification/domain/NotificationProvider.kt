package so.codex.hawk.notification.domain

import io.reactivex.rxjava3.core.Observable
import so.codex.hawk.notification.model.NotificationModel

/**
 * Interface who can provide information to display notification
 */
interface NotificationProvider {
    /**
     * Get stream of notification to display
     * @return steam of notification
     */
    fun observeNotification(): Observable<NotificationModel>
}