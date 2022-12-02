import java.io.File

inline val <reified T> T.inputFile: File
    get() =
        File("input/${T::class.simpleName!!.lowercase()}.txt")

inline val <reified T> T.input: String get() = inputFile.readText(Charsets.UTF_8)

inline val <reified T> T.inputAsLines: List<String> get() = inputFile.readLines(Charsets.UTF_8)

inline fun <reified T, V> T.useInputLines(block: (Sequence<String>) -> V): V = inputFile.useLines(Charsets.UTF_8, block)