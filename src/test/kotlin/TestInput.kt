import java.io.File

inline val <reified T> T.testInputFile: File
    get() =
        File("input/test/${T::class.simpleName!!.lowercase().removeSuffix("test")}.txt")

inline val <reified T> T.testInput: String get() = testInputFile.readText(Charsets.UTF_8)

inline val <reified T> T.testInputAsLines: List<String> get() = testInputFile.readLines(Charsets.UTF_8)

inline fun <reified T, V> T.useTestInputLines(block: (Sequence<String>) -> V): V =
    testInputFile.useLines(Charsets.UTF_8, block)