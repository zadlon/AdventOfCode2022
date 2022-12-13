import Day13.compareTo
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

internal class Day13Test : DayTest() {

    @TestFactory
    fun `Xd`() =
        listOf(
            listOf("[1,1,3,1,1]", "[1,1,5,1,1]") to -1,
            listOf("[[1],[2,3,4]]", "[[1],4]") to -1,
            listOf("[9]", "[[8,7,6]]") to 1,
            listOf("[[4,4],4,4]", "[[4,4],4,4,4]") to -1,
            listOf("[7,7,7,7]", "[7,7,7]") to 1,
            listOf("[]", "[3]") to -1,
            listOf("[[[]]]", "[[]]") to 1,
            listOf("[1,[2,[3,[4,[5,6,7]]]],8,9]", "[1,[2,[3,[4,[5,6,0]]]],8,9]") to 1
        ).map { (input, expected) ->
            DynamicTest.dynamicTest("$input, ${expected}") {
                input.map { Json.parseToJsonElement(it) }
                    .let { assertEquals(expected, it[0].compareTo(it[1])) }
            }
        }

    @Test
    fun LOL() {
        assertEquals(13, Day13.part1(testInput))
    }

    @Test
    fun next() {
        assertEquals(140, Day13.part2(testInput))
    }
}