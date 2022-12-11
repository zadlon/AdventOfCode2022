import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day11Test : DayTest() {

    @Test
    internal fun `should find monkey business`() {
        assertEquals(2713310158, Day11.part2(testInput))
    }
}