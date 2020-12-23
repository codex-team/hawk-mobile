package so.codex.hawk.entity

/**
 * The same class as [Workspace]
 * but without projects
 */
data class WorkspaceCut(
    var id: String = "",
    var name: String = "",
    var description: String = "",
    var balance: Long = 0
)