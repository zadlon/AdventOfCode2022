object Day3 {

    fun part1(sequence: Sequence<String>): Int =
        sequence
            .map { line ->
                val halfOfString = line.length / 2
                val firstCompartment = line.substring(0..halfOfString)
                val secondCompartmentSet = line.substring(halfOfString).toSet()
                val commonItem = firstCompartment.find { it in secondCompartmentSet }!!
                commonItem.priority
            }
            .sum()

    fun part2(sequence: Sequence<String>): Int =
        sequence
            .mapSequence { PackByNIterator(it, 3) }
            .map { bags ->
                var commonObjects = bags[0].toSet()
                for (i in 1 until bags.size) {
                    commonObjects = commonObjects intersect bags[i].toSet()
                }
                commonObjects.first().priority
            }
            .sum()


    val Char.priority: Int
        get() =
            when {
                isUpperCase() -> this - 'A' + 27
                else -> this - 'a' + 1
            }
}

class PackByNIterator<T>(
    private val source: Iterator<T>,
    private val packSize: Int
) : Iterator<List<T>> {

    override fun hasNext(): Boolean = source.hasNext()

    override fun next(): List<T> {
        val res = ArrayList<T>(packSize)
        var idx = 0
        while (source.hasNext() && idx < packSize) {
            res += source.next()
            idx++
        }
        return res
    }

}

fun main() {
    println("Part 1: ${Day3.useInputLines(Day3::part1)}")
    println("Part 2: ${Day3.useInputLines(Day3::part2)}")
}