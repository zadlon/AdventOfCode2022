package common

import java.util.concurrent.ConcurrentHashMap

typealias Function1<T, R> = (T) -> R
typealias Function3<T1, T2, T3, R> = (T1, T2, T3) -> R

fun <T, R> Function1<T, R>.memoize(): Function1<T, R> = memoize(ConcurrentHashMap())

fun <T, R> Function1<T, R>.memoize(size: Int): Function1<T, R> = memoize(ConcurrentHashMap(mapCapacity(size)))

fun <T, R> Function1<T, R>.memoize(cache: MutableMap<T, R>): Function1<T, R> = memoize(cache) { it }

inline fun <T, K, R> Function1<T, R>.memoize(
    cache: MutableMap<K, R>,
    crossinline keyExtractor: (T) -> K
): Function1<T, R> = object : Function1<T, R> {
    override fun invoke(p1: T): R = cache.getOrPut(keyExtractor(p1)) { this@memoize(p1) }
}

fun <T1, T2, T3, R> Function3<T1, T2, T3, R>.memoize(): Function3<T1, T2, T3, R> = memoize(ConcurrentHashMap())

fun <T1, T2, T3, R> Function3<T1, T2, T3, R>.memoize(size: Int): Function3<T1, T2, T3, R> =
    memoize(ConcurrentHashMap(mapCapacity(size)))

fun <T1, T2, T3, R> Function3<T1, T2, T3, R>.memoize(cache: MutableMap<Triple<T1, T2, T3>, R>): Function3<T1, T2, T3, R> =
    memoize(cache) { p1, p2, p3 -> Triple(p1, p2, p3) }

inline fun <T1, T2, T3, K, R> Function3<T1, T2, T3, R>.memoize(
    cache: MutableMap<K, R>,
    crossinline keyExtractor: (T1, T2, T3) -> K
): Function3<T1, T2, T3, R> =
    object : Function3<T1, T2, T3, R> {
        override fun invoke(p1: T1, p2: T2, p3: T3): R =
            cache.getOrPut(keyExtractor(p1, p2, p3)) { this@memoize(p1, p2, p3) }
    }
