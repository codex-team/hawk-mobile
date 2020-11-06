package so.codex.hawk.entity

/**
 * Class containing data for the session.
 *
 * @property token Data instance of the [Token] class.
 *
 * @property time Stores the time of the last session update.
 *                Must be passed into an instance by the developer.
 */
data class Session(val token: Token, val time: Long)
