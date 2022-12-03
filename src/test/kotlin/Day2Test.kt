import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day2Test {

    @Test
    internal fun `should return total score for Part 1`() {
        assertScore(15, Day2.Part1)
    }

    @Test
    internal fun `should return total score for Part 2`() {
        assertScore(12, Day2.Part2)
    }

    private fun assertScore(expectedScore: Int, roundScoreReader: RoundScoreReader) {
        assertEquals(expectedScore, useTestInputLines { Day2.computeScore(it, roundScoreReader) })
    }
}