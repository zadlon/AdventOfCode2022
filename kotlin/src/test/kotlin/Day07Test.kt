import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day07Test : DayTest() {

    @Test
    fun `should sum up size of directories`() {
        assertEquals(95437, Day07.part1(testInput))
    }

    @Test
    fun `should find min directory`() {
        assertEquals(24933642, Day07.part2(testInput))
    }
}