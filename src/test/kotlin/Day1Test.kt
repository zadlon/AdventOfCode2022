import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day1Test {

    @Test
    internal fun `should return max calories by text`() {
        assertEquals(24_000, Day1.findMaxCaloriesByText(testInput))
    }

    @Test
    internal fun `should return max calories by sequence`() {
        assertEquals(24_000, useTestInputLines(Day1::findMaxCaloriesBySequence))
    }

    @Test
    internal fun `should return top3 calories by text`() {
        assertEquals(45_000, Day1.findTotalForTop3ByText(testInput))
    }

    @Test
    internal fun `should return top3 calories by sequence`() {
        assertEquals(45_000, useTestInputLines(Day1::findTotalForTop3BySequence))
    }
}