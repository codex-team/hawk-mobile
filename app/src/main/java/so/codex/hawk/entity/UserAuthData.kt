package so.codex.hawk.entity

/**
 * Class containing user credentials
 *
 * @property email contains the user's email
 *
 * @property password contains the user's password
 */
data class UserAuthData(val email: String, val password: String)
