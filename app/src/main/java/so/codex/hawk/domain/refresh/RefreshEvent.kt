package so.codex.hawk.domain.refresh

/**
 * The class is an enumeration of various events that can occur when trying to update a token.
 */
enum class RefreshEvent {
    /**
     * @property REFRESH_FAILED an event that occurs if the current refresh token has expired and
     *                          the token cannot be refreshed.
     */
    REFRESH_FAILED,

    /**
     * @property REFRESH_SUCCESS an event that occurs if the token refresh was successful.
     */
    REFRESH_SUCCESS,

    /**
     * @property NO_INTERNET the event is raised when the device has problems and
     *                       it is not possible to check the freshness of the token.
     */
    NO_INTERNET,

    /**
     * @property NO_AUTHORIZED The event is raised when cannot found information in storage
     */
    NO_AUTHORIZED
}
