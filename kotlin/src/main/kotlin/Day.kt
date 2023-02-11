import common.FileInput
import common.Input
import java.io.File
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

abstract class Day<T, V> {

    open val defaultInput: Input
        get() {
            val day = this::class.java.simpleName.removePrefix("Day")
            val filename = "../input/$day/input.txt"
            return File(filename)
                .also { if (!it.exists()) throw IllegalArgumentException("[$filename] does not exist.") }
                .let { FileInput(it) }
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