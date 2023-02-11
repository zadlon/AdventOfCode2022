import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day09Test : DayTest() {

    @Test
    fun `should count positions visited at least once for rope of size 2`() {
        assertEquals(13, Day09.part1(testInputPart1))
    }

    @Test
    fun `should count positions visited at least once for rope of size 10`() {
        assertEquals(36, Day09.part2(testInputPart2))
    }
}