import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day21Test : DayTest() {

    @Test
    fun `should return what root monkey would yell`() {
        assertEquals(152L, Day21.part1(testInput))
    }

    @Test
    fun `should return what human would yell`() {
        assertEquals(301L, Day21.part2(testInput))
    }
}