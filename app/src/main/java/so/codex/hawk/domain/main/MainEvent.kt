package so.codex.hawk.domain.main

import so.codex.hawk.entity.WorkspaceCut

/**
 * The class is a representation of various events
 * that can occur during being in main screen.
 */
sealed class MainEvent {

    /**
     * An event that fetching workspaces has success
     * @property workspaces given workspaces by event
     */
    class WorkspacesSuccessEvent(val workspaces: List<WorkspaceCut>) : MainEvent()
}
