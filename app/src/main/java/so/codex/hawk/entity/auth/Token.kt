package so.codex.hawk.entity.auth

/**
 * Class containing information about access token and refresh token.
 *
 * @property accessToken contains information about the access token as a string.
 *
 * @property refreshToken  contains information about the refresh token as a string.
 */
data class Token(val accessToken: String, val refreshToken: String)
