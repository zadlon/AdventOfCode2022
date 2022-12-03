import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day03Test : DayTest() {

    @Test
    fun `should find total priority`() {
        assertEquals(157, Day03.part1(input))
    }

    @Test
    fun `should find badges total priority`() {
        assertEquals(70, Day03.part2(input))
    }

}