object Day01 : Day<Int, Int>() {

    private val BLANK_LINE_REGEX = Regex("${System.lineSeparator()}\\s*${System.lineSeparator()}")

    private fun String.toElves(): List<String> = trim().split(BLANK_LINE_REGEX)

    private fun String.toCalories(): Int = split(System.lineSeparator()).sumOf(String::toInt)

    override fun part1(input: Input): Int = input.useContentLines { findMaxCaloriesBySequence(it) }

    override fun part2(input: Input): Int = input.useContentLines { findTotalForTop3BySequence(it) }

    //Part 1:

    private fun findMaxCaloriesByText(input: String) =
        input.toElves()
            .maxOf { elf -> elf.toCalories() }

    private fun findMaxCaloriesBySequence(input: Sequence<String>): Int =
        input.mapSequence { CaloriesIterator(it) }
            .max()


    //Part 2:

    private fun findTotalForTop3ByText(input: String): Int =
        input.toElves()
            .map { elf -> elf.toCalories() }
            .nMax(3)
            .sum()

    private fun findTotalForTop3BySequence(input: Sequence<String>): Int =
        input.mapSequence { CaloriesIterator(it) }
            .nMax(3)
            .sum()
}

private class CaloriesIterator(private val strings: Iterator<String>) : Iterator<Int> {

    override fun hasNext(): Boolean = strings.hasNext()

    override fun next(): Int {
        var caloriesOnElf = 0
        while (strings.hasNext()) {
            val calories = strings.next()
            if (calories.isBlank()) {
                break
            }
            caloriesOnElf += calories.toInt()
        }
        return caloriesOnElf
    }

}