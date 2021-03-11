package so.codex.hawk.entity

/**
 * Class containing data about project
 * @property id an id of project
 * @property name name of current project
 * @property description a description of project
 * @property image url.
 * @property unreadCount returns number of unread events
 */
data class Project(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val image: String = "",
    val unreadCount: Int = 0,
    val lastEvent: Event = Event.NO_LAST_EVENT
)
