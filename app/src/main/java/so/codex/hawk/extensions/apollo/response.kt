package so.codex.hawk.extensions.apollo

import com.apollographql.apollo.api.Response
import so.codex.hawk.RefreshMutation
import so.codex.hawk.SessionKeeper
import so.codex.hawk.WorkspacesQuery
import so.codex.hawk.entity.Event
import so.codex.hawk.entity.Project
import so.codex.hawk.entity.Workspace
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

/**
 * Extension method for converting [WorkspacesQuery] response models from Apollo to custom model.
 *
 * @return [Workspace] custom response model.
 */
fun WorkspacesQuery.Workspace.toWorkspace(): Workspace {
    return Workspace(
        id,
        name ?: "",
        image ?: "",
        description ?: "",
        balance.toString().toLong(),
        projects?.map {
            it.toProject()
        } ?: emptyList()
    )
}

/**
 * Extension method for converting [WorkspacesQuery] response models from Apollo to custom model.
 *
 * @return [Project] custom response model.
 */
fun WorkspacesQuery.Project.toProject(): Project {
    return Project(
        id,
        name,
        description ?: "",
        image ?: "",
        unreadCount,
        recentEvents?.events?.get(0)?.toEvent()?:Event()
    )
}

fun WorkspacesQuery.Event.toEvent(): Event {
    return Event(
        id,
        payload.title,
        groupHash,
        payload.timestamp
    )
}