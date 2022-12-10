import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day10Test : DayTest() {

    @Test
    internal fun part1() {
        assertEquals(13140, Day10.part1(testInput))
    }

    private val part2ExpectedResult =
        """
        |##..##..##..##..##..##..##..##..##..##..
        |###...###...###...###...###...###...###.
        |####....####....####....####....####....
        |#####.....#####.....#####.....#####.....
        |######......######......######......####
        |#######.......#######.......#######.....
        """.trim().trimMargin()

    @Test
    internal fun part2() {
        assertEquals(part2ExpectedResult, Day10.part2(testInput).trim())
    }
}