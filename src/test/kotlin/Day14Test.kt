import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day14Test : DayTest() {

    @Test
    internal fun `should count units of sand at rest before it flows in the abyss`() {
        assertEquals(24, Day14.part1(testInput))
    }

    @Test
    internal fun `should count units of sand at rest when the top is reached`() {
        assertEquals(93, Day14.part2(testInput))
    }
}