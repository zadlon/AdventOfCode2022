import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day25Test: DayTest() {

    @Test
    internal fun `should return SNAFU number`() {
        assertEquals("2=-1=0", Day25.part1(testInput))
    }
}