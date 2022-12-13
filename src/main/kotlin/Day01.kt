typealias Calories = String
typealias Elf = List<Calories>

object Day01 : Day<Int, Int>() {

    override fun part1(input: Input): Int = input.useContentLines { findMaxCaloriesBySequence(it) }

    override fun part2(input: Input): Int = input.useContentLines { findTotalForTop3BySequence(it) }

    //Part 1:

    private fun findMaxCaloriesBySequence(input: Sequence<String>): Int =
        input
            .group()
            .maxOf { elf -> elf.countCarryingCalories() }

    //Part 2:

    private fun findTotalForTop3BySequence(input: Sequence<String>): Int =
        input
            .group()
            .map { elf -> elf.countCarryingCalories() }
            .nMax(3)
            .sum()

    private fun Elf.countCarryingCalories(): Int = sumOf { calories -> calories.toInt() }
}