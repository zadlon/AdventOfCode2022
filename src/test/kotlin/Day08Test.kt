import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day08Test : DayTest() {

    @Test
    fun `should count visible trees`() {
        assertEquals(21, Day08.part1(testInput))
    }

    @Test
    fun `should find tree scenic score`() {
        assertEquals(8, Day08.part2(testInput))
    }
}