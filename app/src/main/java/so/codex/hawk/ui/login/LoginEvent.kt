package so.codex.hawk.ui.login

/**
 * The class is an enumeration of various events that can occur during login.
 */
enum class LoginEvent {
    /**
     * @property INTERNET_ERROR An event that occurs when the Internet is bad or completely absent.
     */
    INTERNET_ERROR,

    /**
     * @property LOGIN_ERROR An event that is raised when an error occurs on the server side.
     *                       For example, the user entered an incorrect email or password.
     *                       The server error will be reformatted into this event.
     */
    LOGIN_ERROR,

    /**
     * @property SUCCESSFUL_LOGIN An event that occurs when the server confirms
     *                            a successful authorization.
     */
    SUCCESSFUL_LOGIN
}
