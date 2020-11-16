package so.codex.hawk.entity

import com.apollographql.apollo.api.Error

/**
 * Class containing information about the response to RefreshTokenMutation.
 * This can be obtained by converting the Apollo response model
 * using the .toRefreshResponse() method.
 *
 * @property token
 * @see [Token]
 *
 * @property hasError contains information about the presence of errors
 *                    in the response from the server side.
 *
 * @property error Server side error list. Will be empty if [hasError] == false.
 */
data class RefreshResponse(val token: Token, val hasError: Boolean, val error: List<Error>)
