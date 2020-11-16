package so.codex.hawk.notification.presenter

import so.codex.hawk.notification.model.NotificationModel

/**
 * Who can show notification
 */
interface NotificationView {
    /**
     * Show notification by [model]
     * @param model Instance to display common information about notification
     */
    fun show(model: NotificationModel)
}