package so.codex.hawk.entity

/**
 * Class containing data about workspace
 * @property id an id of workspace
 * @property name name of current workspace
 * @property description a description of workspace
 * @property image url
 * @property balance workspace's balance
 * @property projects current projects of workspace
 */
data class Workspace(
    val id: String = "",
    val name: String = "",
    val image: String = "",
    val description: String = "",
    val balance: Long = 0,
    val projects: List<Project> = listOf(),
)
