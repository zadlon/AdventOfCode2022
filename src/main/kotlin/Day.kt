abstract class Day<T, V> {

    fun run() {
        val resourceName = "input/${this::class.java.simpleName.lowercase()}.txt"
        Day::class.java.getResource(resourceName)
            ?.let { url -> run(Input(url.file)) }
            ?: throw IllegalArgumentException("Resource [$resourceName] not found")
    }

    fun run(input: Input) {
        println("""
            - Part 1: ${part1(input)}
            - Part 2: ${part2(input)}
        """.trimIndent()
        )
    }

    abstract fun part1(input: Input): T

    abstract fun part2(input: Input): V
}