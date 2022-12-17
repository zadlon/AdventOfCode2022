import common.FileInput
import common.Input

internal abstract class DayTest {

    internal val testInput: Input get() = getTestInput()

    internal val testInputPart1: Input get() = getTestInput("_part1")

    internal val testInputPart2: Input get() = getTestInput("_part2")

    private fun getTestInput(suffix: String = "") : Input {
        val path = "test/${this::class.java.simpleName.lowercase().removeSuffix("test")}$suffix.txt"
        return this::class.java.getResource(path)
            ?.let { FileInput(it.file) }
            ?: throw IllegalArgumentException("Cannot find resources for test class: ${this::class.java.simpleName} with path: [$path]")
    }

}