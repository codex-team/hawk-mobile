package so.codex.hawk.extensions.apollo

import com.apollographql.apollo.api.Response
import so.codex.hawk.RefreshMutation
import so.codex.hawk.SessionKeeper
import so.codex.hawk.entity.auth.RefreshResponse
import so.codex.hawk.entity.auth.Token

/**
 * Extension method for converting [RefreshMutation] response models from Apollo to custom model.
 *
 * @return [RefreshResponse] custom response model.
 */
fun Response<RefreshMutation.Data>.toRefreshResponse(): RefreshResponse {
    return if (!hasErrors() && data != null) {
        val accessToken = data!!.refreshTokens.accessToken
        val refreshToken = data!!.refreshTokens.refreshToken
        RefreshResponse(Token(accessToken, refreshToken), hasError = false, emptyList())
    } else {
        val errors = this.errors ?: emptyList()
        RefreshResponse(SessionKeeper.EMPTY_TOKEN, hasError = true, errors)
    }
}
