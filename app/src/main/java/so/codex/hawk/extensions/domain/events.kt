package so.codex.hawk.extensions.domain

import so.codex.hawk.domain.refresh.RefreshEvent
import so.codex.hawk.domain.splash.SplashEvent

/**
 * Extension method for converting token [RefreshEvent] to [SplashScreen] events.
 *
 * @return [SplashEvent]
 */
fun RefreshEvent.toSplashEvent(): SplashEvent {
    return when (this) {
        RefreshEvent.REFRESH_FAILED -> SplashEvent.SESSION_EXPIRED
        RefreshEvent.REFRESH_SUCCESS -> SplashEvent.SESSION_ACTIVE
        RefreshEvent.NO_INTERNET -> SplashEvent.NO_INTERNET
    }
}
