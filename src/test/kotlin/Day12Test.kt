import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day12Test : DayTest() {

    @Test
    fun `should count steps from start`() {
        assertEquals(31, Day12.part1(testInput))
    }

    @Test
    fun `should count steps from multiple starts`() {
        assertEquals(29, Day12.part2(testInput))
    }
}