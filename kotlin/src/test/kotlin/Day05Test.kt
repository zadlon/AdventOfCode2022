import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day05Test : DayTest() {

    @Test
    internal fun `should return first line of cargo for CrateMover 9000`() {
        assertEquals("CMZ", Day05.part1(testInput))
    }

    @Test
    internal fun `should return first line of cargo for CrateMover 9001`() {
        assertEquals("MCD", Day05.part2(testInput))
    }
}