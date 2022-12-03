import java.io.File

class Input(inputFilePath: String) {

    @PublishedApi
    internal val file: File = File(inputFilePath)

    val contentText by lazy { file.readText(Charsets.UTF_8) }

    val contentLines by lazy { file.readLines(Charsets.UTF_8) }

    inline fun <T> useContentLines(block: (Sequence<String>) -> T): T = file.useLines(Charsets.UTF_8) { block(it) }
}