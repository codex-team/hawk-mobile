package so.codex.hawk.entity

/**
 * Class containing data about workspace
 * @property id an id of workspace
 * @property name name of current workspace
 * @property description a description of workspace
 * @property balance workspace's balance
 * @property projects current projects of workspace
 */
data class Workspace(
    var id: String? = "",
    var name: String? = "",
    var description: String? = "",
    var balance: Long? = 0,
    var projects: List<Project>? = null
)
