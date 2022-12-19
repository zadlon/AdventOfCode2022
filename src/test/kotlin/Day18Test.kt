import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day18Test : DayTest() {

    @Test
    internal fun `should find surface area`() {
        assertEquals(64, Day18.part1(testInput))
    }

    @Test
    internal fun `should find exterior surface area`() {
        assertEquals(58, Day18.part2(testInput))
    }
}