import java.util.PriorityQueue

inline fun <T, V> Sequence<T>.mapSequence(crossinline operation: (Iterator<T>) -> Iterator<V>): Sequence<V> =
    Sequence { operation(this.iterator()) }

fun <T : Comparable<T>> Sequence<T>.nMax(n: Int): List<T> = nMax(n, naturalOrder())

fun <T> Sequence<T>.nMax(n: Int, comparator: Comparator<T>): List<T> {
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

