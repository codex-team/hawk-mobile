package so.codex.hawk.entity

import so.codex.hawk.ui.main.lists.HasId

/**
 * The same class as [Workspace]
 * but without projects
 */
data class WorkspaceCut(
    val id: String = "",
    val name: String = "",
    val image: String = "",
    val description: String = "",
    val balance: Long = 0
)