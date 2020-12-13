package so.codex.hawk.extensions

import so.codex.hawk.entity.Workspace
import so.codex.hawk.entity.WorkspaceCut

fun Workspace.toCut(): WorkspaceCut {
    return WorkspaceCut(id, name, description, balance)
}