import Day13.compareTo
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

internal class Day13Test : DayTest() {

    @TestFactory
    fun `should compare packets pairs`() =
        listOf(
            listOf("[1,1,3,1,1]", "[1,1,5,1,1]") to -1,
            listOf("[[1],[2,3,4]]", "[[1],4]") to -1,
            listOf("[9]", "[[8,7,6]]") to 1,
            listOf("[[4,4],4,4]", "[[4,4],4,4,4]") to -1,
            listOf("[7,7,7,7]", "[7,7,7]") to 1,
            listOf("[]", "[3]") to -1,
            listOf("[[[]]]", "[[]]") to 1,
            listOf("[1,[2,[3,[4,[5,6,7]]]],8,9]", "[1,[2,[3,[4,[5,6,0]]]],8,9]") to 1,
            listOf("[3]","[3]") to 0,
            listOf("[]", "[]") to 0,
            listOf("[[3],[4,3],2,[[]]]", "[[3],[4,3],2,[[]]]") to 0
        ).map { (input, expected) ->
            DynamicTest.dynamicTest("${input[0]} should be ${convertResult(expected)} than ${input[1]}") {
                input.map { Json.parseToJsonElement(it) }
                    .let { assertEquals(expected, it[0].compareTo(it[1])) }
            }
        }

    private fun convertResult(int: Int): String =
        when {
            int < 0 -> "lesser than"
            int > 0 -> "greater than"
            else -> "equal to"
        }

    @Test
    fun `should find sum of indices of pairs in right order`() {
        assertEquals(13, Day13.part1(testInput))
    }

    @Test
    fun `should find the decoder key`() {
        assertEquals(140, Day13.part2(testInput))
    }
}