import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day01Test : DayTest() {

    @Test
    internal fun `should return max calories`() {
        assertEquals(24_000, Day01.part1(testInput))
    }

    @Test
    internal fun `should return top3 calories`() {
        assertEquals(45_000, Day01.part2(testInput))
    }
}