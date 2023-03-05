import common.Input
import java.util.ArrayDeque

object Day25 : Day<String, String>() {
    override fun part1(input: Input): String =
        input.useContentLines { lines -> lines.sumOf { line -> line.readSnafu() } }.writeSnafu()

    override fun part2(input: Input): String = ""

    private fun String.readSnafu(): Long {
        var res = 0L
        var step = 1L
        for (i in lastIndex downTo 0) {
            res += this[i].readSnafu() * step
            step *= 5L
        }
        return res
    }

    private fun Char.readSnafu(): Int = when (this) {
        '0' -> 0
        '1' -> 1
        '2' -> 2
        '-' -> -1
        '=' -> -2
        else -> throw IllegalArgumentException()
    }

    private fun Long.writeSnafu(): String {
        val res = ArrayDeque<Char>()
        var q = this
        while (q != 0L) {
            val rem = q % 5
            q /= 5
            val char = when (rem) {
                0L -> '0'
                1L -> '1'
                2L -> '2'
                3L -> {
                    q++
                    '='
                }
                else -> {
                    q++
                    '-'
                }
            }
            res.addFirst(char)
        }
        return String(res.toCharArray())
    }
}