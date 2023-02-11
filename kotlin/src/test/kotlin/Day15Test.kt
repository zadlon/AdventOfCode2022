import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day15Test : DayTest() {

    @AfterEach
    fun resetDay15() {
        Day15.part1Row = 2_000_000
        Day15.part2CoordinatesMax = 4_000_000
    }

    @Test
    fun `should count all positions that cannot contain beacon on a line`() {
        Day15.part1Row = 10
        assertEquals(26, Day15.part1(testInput))
    }

    @Test
    fun `should find tuning frequency of missing beacon`() {
        Day15.part2CoordinatesMax = 20
        assertEquals(56000011, Day15.part2(testInput))
    }
}