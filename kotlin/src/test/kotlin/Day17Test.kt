import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day17Test: DayTest() {

    @Test
    fun `should find height for 2022 rocks`() {
        assertEquals(3068, Day17.part1(testInput))
    }

    @Test
    fun `should find height for 1 trillion rocks`() {
        assertEquals(1_514_285_714_288, Day17.part2(testInput))
    }
}