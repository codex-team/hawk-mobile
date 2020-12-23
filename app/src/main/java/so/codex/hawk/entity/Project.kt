package so.codex.hawk.entity

/**
 * Class containing data about project
 * @property id an id of project
 * @property name name of current project
 * @property description a description of project
 * @property unreadCount returns number of unread events
 */
data class Project(
    var id: String = "",
    var name: String = "",
    var description: String = "",
    var unreadCount: Int = 0
)
