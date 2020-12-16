package so.codex.hawk.extensions

import io.reactivex.rxjava3.core.Observable
import so.codex.hawk.entity.Workspace
import so.codex.hawk.entity.WorkspaceCut

/**
 * Function for cutting workspaces
 * (make them without projects)
 */
fun Workspace.toCut(): WorkspaceCut {
    return WorkspaceCut(id, name, description, balance)
}

/**
 * Map current source and return non-null item
 * @return non-null item
 */
inline fun <T : Any, R : Any> Observable<T>.mapNotNull(crossinline transform: (T) -> R?): Observable<R> {
    return flatMap {
        val res = transform(it)
        if (res == null) return@flatMap Observable.empty()
        else return@flatMap Observable.just(res)
    }
}