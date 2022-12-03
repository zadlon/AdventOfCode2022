import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day02Test : DayTest() {

    @Test
    internal fun `should return total score for Part 1`() {
        assertEquals(15, Day02.part1(input))
    }

    @Test
    internal fun `should return total score for Part 2`() {
        assertEquals(12, Day02.part2(input))
    }
}