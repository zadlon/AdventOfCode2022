import kotlin.math.sign

object Day09 : Day<Int, Int>() {

    override fun part1(input: Input): Int = input.useContentLines { solve(it, 2) }

    override fun part2(input: Input): Int = input.useContentLines { solve(it, 10) }


    private fun solve(lines: Sequence<String>, size: Int): Int =
        lines.flatMap { line -> readLine(line) }
            .runningFold(Rope.of(size)) { rope, direction -> rope.move(direction) }
            .map { rope -> rope.tail }
            .distinct()
            .count()

    private fun readLine(line: String): List<Direction> =
        List(line.substring(2).toInt()) { Direction.of(line.first()) }

    data class Rope(private val knots: List<Knot>) {

        companion object {
            fun of(size: Int): Rope = Rope(List(size) { Knot(0, 0) })
        }

        val head: Knot = knots.first()

        val tail: Knot = knots.last()

        fun move(direction: Direction): Rope =
            buildList(knots.size) {
                val nextHead = when (direction) {
                    Direction.UP -> head.copy(y = head.y + 1)
                    Direction.DOWN -> head.copy(y = head.y - 1)
                    Direction.LEFT -> head.copy(x = head.x - 1)
                    Direction.RIGHT -> head.copy(x = head.x + 1)
                }
                add(nextHead)
                for (i in 1 until knots.size) {
                    add(knots[i].follow(this@buildList[i - 1]))
                }
            }.let { Rope(it) }
    }

    data class Knot(val x: Int, val y: Int) {

        fun follow(previousKnot: Knot): Knot {
            if (this == previousKnot) {
                return this
            }
            val diffX = (previousKnot.x - x)
            val diffY = (previousKnot.y - y)
            return if (diffX !in -1..1 || diffY !in -1..1) {
                Knot(
                    x = x + diffX.sign,
                    y = y + diffY.sign
                )
            } else {
                this
            }
        }
    }

    enum class Direction {
        RIGHT, UP, LEFT, DOWN;

        companion object {
            fun of(c: Char): Direction =
                when (c) {
                    'R' -> RIGHT
                    'U' -> UP
                    'L' -> LEFT
                    'D' -> DOWN
                    else -> throw IllegalArgumentException()
                }
        }
    }
}