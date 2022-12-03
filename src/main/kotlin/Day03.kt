object Day03 : Day<Int, Int>() {

    override fun part1(input: Input): Int = input.useContentLines { commonItemBetweenCompartments(it) }

    override fun part2(input: Input): Int = input.useContentLines { commonItemBetweenThreeBags(it) }

    private fun commonItemBetweenCompartments(sequence: Sequence<String>): Int =
        sequence
            .sumOf { line ->
                val halfOfString = line.length / 2
                val firstCompartment = line.substring(0..halfOfString)
                val secondCompartmentSet = line.substring(halfOfString).toSet()
                val commonItem = firstCompartment.find { it in secondCompartmentSet }!!
                commonItem.priority
            }

    private fun commonItemBetweenThreeBags(sequence: Sequence<String>): Int =
        sequence
            .chunked(3)
            .sumOf { bags ->
                bags.map(String::toSet)
                    .reduce { acc, chars -> acc intersect chars }
                    .first()
                    .priority
            }


    private val Char.priority: Int
        get() =
            when {
                isUpperCase() -> this - 'A' + 27
                else -> this - 'a' + 1
            }
}