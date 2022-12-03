abstract class Day<T, V> {

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