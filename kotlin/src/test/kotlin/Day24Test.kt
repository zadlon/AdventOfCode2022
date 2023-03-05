import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day24Test : DayTest() {

    @Test
    fun `should find fewest number of minutes to avoid the blizzards and reach the goal`() {
        assertEquals(18, Day24.part1(testInput))
    }

    @Test
    fun `should find fewest number of minutes to avoid the blizzards and reach the goal, go back to the start then reach the goal again`() {
        assertEquals(54, Day24.part2(testInput))
    }

}