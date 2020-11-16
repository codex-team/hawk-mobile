package so.codex.hawk.notification.model

/**
 * The type of notification to display messages by priority
 */
enum class NotificationType {
    /**
     * Notification have only informative information for user
     */
    INFO,

    /**
     * In while running code, occurred error without any dangerous effects
     */
    WARNING,

    /**
     * Something went wrong and this functionality is broken
     */
    ERROR
}