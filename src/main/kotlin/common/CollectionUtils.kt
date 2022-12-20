package common

import java.util.PriorityQueue

fun Sequence<String>.group() = group { line -> line.isBlank() }

inline fun <T> Sequence<T>.group(crossinline separator: (T) -> Boolean): Sequence<List<T>> =
    Sequence { iterator().group(separator) }

inline fun <T> Iterator<T>.group(crossinline separator: (T) -> Boolean): Iterator<List<T>> = object : Iterator<List<T>> {

    override fun hasNext(): Boolean = this@group.hasNext()

    override fun next(): List<T> {
        val res = mutableListOf<T>()
        while (this@group.hasNext()) {
            val next = this@group.next()
            if (separator(next)) {
                break
            }
            res += next
        }
        return res
    }
}

fun <T : Comparable<T>> Sequence<T>.nMax(n: Int): List<T> = nMax(n, naturalOrder())

fun <T : Comparable<T>> Iterable<T>.nMax(n: Int): List<T> = nMax(n, naturalOrder())

fun <T : Comparable<T>> Iterator<T>.nMax(n: Int): List<T> = nMax(n, naturalOrder())

fun <T> Iterable<T>.nMax(n: Int, comparator: Comparator<T>): List<T> = iterator().nMax(n, comparator)

fun <T> Sequence<T>.nMax(n: Int, comparator: Comparator<T>): List<T> = iterator().nMax(n, comparator)

fun <T> Iterator<T>.nMax(n: Int, comparator: Comparator<T>): List<T> {
    val pq = PriorityQueue(n, comparator)
    for (item in this) {
        if (pq.size < n) {
            pq.add(item)
        } else if (comparator.compare(pq.peek(), item) < 0) {
            pq.poll()
            pq.add(item)
        }
    }
    return pq.toList()
}

fun <T> List<T>.circular(): Iterable<T> = object : Iterable<T> {
    override fun iterator(): Iterator<T> = this@circular.circularIterator()
}

fun <T> List<T>.circularWithIndex(): Iterable<IndexedValue<T>> = object : Iterable<IndexedValue<T>> {
    override fun iterator(): Iterator<IndexedValue<T>> = this@circularWithIndex.circularWithIndexIterator()
}

fun <T> List<T>.circularIterator(): Iterator<T> = object : Iterator<T> {

    private var source: Iterator<T> = this@circularIterator.iterator()

    override fun hasNext(): Boolean = this@circularIterator.isNotEmpty()

    override fun next(): T =
        when {
            source.hasNext() -> source.next()
            else -> this@circularIterator.iterator().also { source = it }.next()
        }

}

fun <T> List<T>.circularWithIndexIterator(): Iterator<IndexedValue<T>> = object : Iterator<IndexedValue<T>> {

    private var source: Iterator<IndexedValue<T>> = this@circularWithIndexIterator.withIndex().iterator()

    override fun hasNext(): Boolean = this@circularWithIndexIterator.isNotEmpty()

    override fun next(): IndexedValue<T> =
        when {
            source.hasNext() -> source.next()
            else -> this@circularWithIndexIterator.withIndex().iterator().also { source = it }.next()
        }

}

private const val INT_MAX_POWER_OF_TWO: Int = 1 shl (Int.SIZE_BITS - 2)

fun mapCapacity(size: Int) =
    when {
        size <= INT_MAX_POWER_OF_TWO -> ((size * 0.75F) + 1).toInt()
        else -> Int.MAX_VALUE
    }
