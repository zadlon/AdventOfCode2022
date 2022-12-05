internal abstract class DayTest {

    internal val testInput: Input

    init {
        val path = "test/${this::class.java.simpleName.lowercase().removeSuffix("test")}.txt"
        testInput = this::class.java.getResource(path)
            ?.let { Input(it.file) }
            ?: throw IllegalArgumentException("Cannot find resources for test class: ${this::class.java.simpleName} with path: [$path]")
    }

}