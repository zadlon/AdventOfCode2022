import common.StringInput
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertEquals

internal class Day06Test {

    @TestFactory
    internal fun `should find first packet marker index`() =
        listOf(
            "mjqjpqmgbljsphdztnvjfqwrcgsmlb" to 7,
            "bvwbjplbgvbhsrlpgdmjqwftvncz" to 5,
            "nppdvjthqldpwncqszvftbrmjlhg" to 6,
            "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg" to 10,
            "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw" to 11
        ).map { (input, expectedValue) ->
            DynamicTest.dynamicTest("[$input] first marker index should be [$expectedValue]") {
                assertEquals(expectedValue, Day06.part1(StringInput(input)))
        }
    }

    @TestFactory
    internal fun `should find first message marker index`() =
        listOf(
            "mjqjpqmgbljsphdztnvjfqwrcgsmlb" to 19,
            "bvwbjplbgvbhsrlpgdmjqwftvncz" to 23,
            "nppdvjthqldpwncqszvftbrmjlhg" to 23,
            "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg" to 29,
            "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw" to 26
        ).map { (input, expectedValue) ->
            DynamicTest.dynamicTest("[$input] first marker index should be [$expectedValue]") {
                assertEquals(expectedValue, Day06.part2(StringInput(input)))
            }
        }
}