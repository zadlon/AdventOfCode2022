import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day04Test : DayTest() {

    @Test
    internal fun `should count pairs with fully contained sections`() {
        assertEquals(2, Day04.part1(input))
    }

    @Test
    internal fun `should count pairs with overlapping sections`() {
        assertEquals(4, Day04.part2(input))
    }
}