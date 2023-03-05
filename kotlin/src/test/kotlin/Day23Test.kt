import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day23Test : DayTest() {

    @Test
    fun `should return empty ground tiles in rectangle`() {
        assertEquals(110, Day23.part1(testInput))
    }

    @Test
    fun `should return first round where no elves moves`() {
        assertEquals(20, Day23.part2(testInput))
    }
}