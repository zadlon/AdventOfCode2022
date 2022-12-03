object Day3 {

    fun part1(): Int = useInputLines { commonItemBetweenCompartments(it) }

    fun part2(): Int = useInputLines { commonItemBetweenThreeBags(it) }

    fun commonItemBetweenCompartments(sequence: Sequence<String>): Int =
        sequence
            .sumOf { line ->
                val halfOfString = line.length / 2
                val firstCompartment = line.substring(0..halfOfString)
                val secondCompartmentSet = line.substring(halfOfString).toSet()
                val commonItem = firstCompartment.find { it in secondCompartmentSet }!!
                commonItem.priority
            }

    fun commonItemBetweenThreeBags(sequence: Sequence<String>): Int =
        sequence
            .chunked(3)
            .sumOf { bags ->
                bags.map(String::toSet)
                    .reduce { acc, chars -> acc intersect chars }
                    .first()
                    .priority
            }


    val Char.priority: Int
        get() =
            when {
                isUpperCase() -> this - 'A' + 27
                else -> this - 'a' + 1
            }
}

fun main() {
    println("Part 1: ${Day3.part1()}")
    println("Part 2: ${Day3.part2()}")
}