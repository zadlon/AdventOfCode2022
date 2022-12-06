internal abstract class DayTest {

    internal val testInput: Input by lazy {
        val path = "test/${this::class.java.simpleName.lowercase().removeSuffix("test")}.txt"
        this::class.java.getResource(path)
            ?.let { FileInput(it.file) }
            ?: throw IllegalArgumentException("Cannot find resources for test class: ${this::class.java.simpleName} with path: [$path]")
    }

}