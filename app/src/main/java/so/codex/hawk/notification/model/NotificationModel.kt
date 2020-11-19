package so.codex.hawk.notification.model

/**
 * Notification model that contains necessary information to display on screen
 */
data class NotificationModel(
    /**
     * @property text Message of notification
     */
    val text: String,
    /**
     * @property type Important of notification
     */
    val type: NotificationType
)