import common.FileInput

fun main(vararg args: String) {
    if (args.isEmpty()) throw IllegalArgumentException("Missing arguments")
    val day = args[0].toIntOrNull()
        ?.also { if (it !in 1..25) throw IllegalArgumentException("day [$it] must be between 1 and 25") }
        ?: throw IllegalArgumentException("day [${args[0]}] must be a number")
    val dayRunner: Day<*, *> = when (day) {
        1 -> Day01
        2 -> Day02
        3 -> Day03
        4 -> Day04
        5 -> Day05
        6 -> Day06
        7 -> Day07
        8 -> Day08
        9 -> Day09
        10 -> Day10
        11 -> Day11
        12 -> Day12
        13 -> Day13
        14 -> Day14
        15 -> Day15
        16 -> Day16
        17 -> Day17
        18 -> Day18
        20 -> Day20
        21 -> Day21
        22 -> Day22
        else -> TODO("${day}th day has not been implemented yet")
    }
    args.getOrNull(1)
        ?.let { filename -> dayRunner.run(FileInput(filename)) }
        ?: run { dayRunner.run() }
}