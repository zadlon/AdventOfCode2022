package common

import java.io.File

sealed interface Input {

    val contentText: String

    val contentLines: List<String>

    fun <T> useContentLines(block: (Sequence<String>) -> T): T
}

class FileInput(private val file: File) : Input {

    constructor(filePath: String): this(File(filePath))

    override val contentText: String
        get() = file.readText(Charsets.UTF_8)

    override val contentLines: List<String>
        get() = file.readLines(Charsets.UTF_8)

    override fun <T> useContentLines(block: (Sequence<String>) -> T): T = file.useLines { block(it) }
}

data class StringInput(private val value: String) : Input {

    override val contentText: String
        get() = value

    override val contentLines: List<String>
        get() = value.lines()

    override fun <T> useContentLines(block: (Sequence<String>) -> T) = block(value.lineSequence())
}
