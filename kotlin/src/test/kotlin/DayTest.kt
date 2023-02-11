import common.FileInput
import common.Input
import java.io.File

internal abstract class DayTest {

    internal val testInput: Input get() = getTestInput()

    internal val testInputPart1: Input get() = getTestInput("_part1")

    internal val testInputPart2: Input get() = getTestInput("_part2")

    private fun getTestInput(suffix: String = "") : Input {
        val day = this::class.java.simpleName.removePrefix("Day").removeSuffix("Test")
        val filename = "../input/$day/test$suffix.txt"
        return File(filename)
            .also { if (!it.exists()) throw IllegalArgumentException("[$filename] does not exist.") }
            .let { FileInput(it) }
    }

}