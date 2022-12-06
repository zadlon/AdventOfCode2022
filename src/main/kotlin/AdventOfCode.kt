fun main(vararg args: String) {
    if (args.isEmpty()) throw IllegalArgumentException("Missing arguments")
    val day = args[0].toIntOrNull()
        ?.also { if (it !in 1..24) throw IllegalArgumentException("day [$it] must be between 1 and 24") }
        ?: throw IllegalArgumentException("day [${args[0]}] must be a number")
    val dayRunner: Day<*, *> = when (day) {
        1 -> Day01
        2 -> Day02
        3 -> Day03
        4 -> Day04
        5 -> Day05
        6 -> Day06
        else -> TODO("${day}th day has not been implemented yet")
    }
    args.getOrNull(1)
        ?.let { filename -> dayRunner.run(FileInput(filename)) }
        ?: run { dayRunner.run() }
}