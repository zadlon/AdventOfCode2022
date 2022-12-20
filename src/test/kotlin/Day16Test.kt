import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

internal class Day16Test : DayTest() {

    @Test
    fun `should find the most pressure that can be released alone`() {
        assertEquals(1651, Day16.part1(testInput))
    }

    @Disabled
    @Test
    fun `should find the most pressure that can be released with a friend`() {
        assertEquals(1707, Day16.part2(testInput))
    }
}