object Day1 {

    private val BLANK_LINE_REGEX = Regex("${System.lineSeparator()}\\s*${System.lineSeparator()}")

    private fun String.toElves(): List<String> = trim().split(BLANK_LINE_REGEX)

    private fun String.toCalories(): Int = split(System.lineSeparator()).sumOf(String::toInt)

    fun part1() = useInputLines { findMaxCaloriesBySequence(it) }

    fun part2() = useInputLines { findTotalForTop3BySequence(it) }

    //Part 1:

    fun findMaxCaloriesByText(input: String) =
        input.toElves()
            .maxOf { elf -> elf.toCalories() }

    fun findMaxCaloriesBySequence(input: Sequence<String>): Int =
        input.mapSequence { CaloriesIterator(it) }
            .max()


    //Part 2:

    fun findTotalForTop3ByText(input: String): Int =
        input.toElves()
            .map { elf -> elf.toCalories() }
            .nMax(3)
            .sum()

    fun findTotalForTop3BySequence(input: Sequence<String>): Int =
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

    fun main() {
        println("Part 1: ${Day1.part1()}")
        println("Part 2: ${Day1.part2()}")
    }