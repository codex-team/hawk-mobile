package so.codex.hawk.domain.main

import so.codex.hawk.entity.User
import so.codex.hawk.entity.WorkspaceCut

sealed class MainEvent {
    class ProfileEvent(val user: User): MainEvent()
    class WorkspacesSuccessEvent(val workspaces: List<WorkspaceCut>) : MainEvent()
}
