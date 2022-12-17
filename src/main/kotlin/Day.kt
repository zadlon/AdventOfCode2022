import common.FileInput
import common.Input
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

abstract class Day<T, V> {

    open val defaultInput: Input
        get() {
            val resourceName = "input/${this::class.java.simpleName.lowercase()}.txt"
            return Day::class.java.getResource(resourceName)
                ?.let { url -> FileInput(url.file) }
                ?: throw IllegalArgumentException("Resource [$resourceName] not found")
        }

    @OptIn(ExperimentalTime::class)
    fun run(input: Input = defaultInput) {
        val (partOneResult, partOneDuration) = measureTimedValue { part1(input) }
        val (partTwoResult, partTwoDuration) = measureTimedValue { part2(input) }
        println(
            """-Part 1 (${partOneDuration.inWholeMilliseconds} ms): 
$partOneResult
-Part 2 (${partTwoDuration.inWholeMilliseconds} ms):
$partTwoResult"""
        )
    }

    abstract fun part1(input: Input): T

    abstract fun part2(input: Input): V
}