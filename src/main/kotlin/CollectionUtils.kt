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

