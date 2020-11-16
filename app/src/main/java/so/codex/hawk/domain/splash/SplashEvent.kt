package so.codex.hawk.domain.splash

/**
 * The class is an enumeration of various events that can occur on a splash screen.
 */
enum class SplashEvent {
    /**
     * @property SESSION_EXPIRED the event is raised when the access token
     *                           stored on the device has expired.
     */
    SESSION_EXPIRED,

    /**
     * @property SESSION_ACTIVE the event is raised when the access token
     *                          stored on the device is active.
     */
    SESSION_ACTIVE,

    /**
     * @property NO_INTERNET the event is raised when the device has problems and
     *                       it is not possible to check the freshness of the token.
     */
    NO_INTERNET
}
