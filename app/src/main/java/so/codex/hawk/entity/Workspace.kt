package so.codex.hawk.entity

data class Workspace(
    var id: String? = "",
    var name: String? = "",
    var description: String? = "",
    var balance: Long? = 0,
    var projects: List<Project>? = null
)
