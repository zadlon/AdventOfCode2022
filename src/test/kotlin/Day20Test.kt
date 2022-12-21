import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day20Test : DayTest() {

    @Test
    fun `should find sum of coordinates after one round`() {
        assertEquals(3, Day20.part1(testInput))
    }

    @Test
    fun `should find sum of coordinates after 10 rounds and with decryption key`() {
        assertEquals(1623178306, Day20.part2(testInput))
    }
}