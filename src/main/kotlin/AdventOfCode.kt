fun main(vararg args: String) {
    if (args.size < 2) throw IllegalArgumentException("Missing arguments")
    val day = args[0].toIntOrNull()
        ?.also { if (it !in 1..24) throw IllegalArgumentException("day must be between 1 and 24") }
        ?: throw IllegalArgumentException("day must be a number")
    val dayRunner: Day<*, *> = when(day) {
        1 -> Day01
        2 -> Day02
        3 -> Day03
        4 -> Day04
        else -> TODO("${day}th day has not been implemented yet")
    }
    dayRunner.run(Input(args[1]))
}
