import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day22Test : DayTest() {

    @Test
    fun `should return password on flat board`() {
        assertEquals(6032, Day22.part1(testInput))
    }

    @Test
    fun `should return password on cube board`() {
        assertEquals(5031, Day22.part2(testInput))
    }
}