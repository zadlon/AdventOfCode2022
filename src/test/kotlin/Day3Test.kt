import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day3Test {

    @Test
    fun `should find total priority`() {
        assertEquals(157, useTestInputLines(Day3::commonItemBetweenCompartments))
    }

    @Test
    fun `should find badges total priority`() {
        assertEquals(70, useTestInputLines(Day3::commonItemBetweenThreeBags))
    }

}