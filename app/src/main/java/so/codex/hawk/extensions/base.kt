package so.codex.hawk.extensions

import io.reactivex.rxjava3.core.Observable

/**
 * Map current source and return non-null item
 * @return non-null item
 */
inline fun <T : Any, R : Any> Observable<T>.mapNotNull(
    crossinline transform: (T) -> R?
): Observable<R> {
    return map {
        transform(it)
    }.filter {
        it != null
    }.map {
        it!!
    }
}