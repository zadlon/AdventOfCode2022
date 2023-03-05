import common.Input
import java.util.LinkedList

object Day24 : Day<Int, Int>() {

    const val WALL = '#'
    const val NONE = '.'

    override fun part1(input: Input): Int {
        val valley: Valley = Valley.of(input)
        val startPosition = Position(0, -1)
        val endPosition = Position(valley.maxWidth - 1, valley.maxHeight)
        return doTrip(valley, startPosition, endPosition).second
    }

    override fun part2(input: Input): Int {
        var valley: Valley = Valley.of(input)
        val startPosition = Position(0, -1)
        val endPosition = Position(valley.maxWidth - 1, valley.maxHeight)
        var totalMinutes = 0
        doTrip(valley, startPosition, endPosition)
            .let { (newValley, minutes) ->
                valley = newValley
                totalMinutes += minutes
            }
        doTrip(valley, endPosition, startPosition)
            .let { (newValley, minutes) ->
                valley = newValley
                totalMinutes += minutes + 1
            }
        doTrip(valley, startPosition, endPosition).let { (_, minutes) -> totalMinutes += minutes + 1 }
        return totalMinutes
    }

    private fun doTrip(initialValley: Valley, startPosition: Position, endPosition: Position): Pair<Valley, Int> {
        var minutes = 0
        var valley = initialValley
        val positions = LinkedList<Position>().apply { add(startPosition) }
        while (positions.isNotEmpty()) {
            valley = valley.next()
            val currentPositionSize = positions.size
            val visitedPositions = mutableSetOf<Position>()

            repeat(currentPositionSize) {
                val currentPosition = positions.poll()
                if (currentPosition == endPosition) {
                    return valley to minutes
                }
                val nextPositions = currentPosition.nextPositions()
                    .filter { (it !in valley && it.x in 0 until valley.maxWidth && it.y in 0 until valley.maxHeight) || it == startPosition || it == endPosition }
                visitedPositions += nextPositions
            }

            positions += visitedPositions
            minutes++
        }
        return valley to minutes
    }

    data class Valley(
        val blizzards: Map<Position, List<Blizzard>>,
        val maxWidth: Int,
        val maxHeight: Int
    ) {

        companion object {
            fun of(input: Input): Valley {
                val lines = input.contentLines.let { it.subList(1, it.lastIndex) }
                val maxHeight = lines.size
                val maxWidth = lines.first().length - 2
                val blizzards = lines.flatMapIndexed { y, line ->
                    buildList {
                        for (x in 0 until maxWidth) {
                            val c = line[x + 1]
                            if (c != WALL && c != NONE) {
                                add(Position(x, y) to listOf(Blizzard.of(c)))
                            }
                        }
                    }
                }.toMap()
                return Valley(blizzards, maxWidth, maxHeight)
            }
        }

        fun next(): Valley =
            buildMap<Position, MutableList<Blizzard>> {
                this@Valley.blizzards.forEach { (position, blizzards) ->
                    val (x, y) = position
                    blizzards.forEach { blizzard ->
                        val nextX = (x + blizzard.dx).mod(maxWidth)
                        val nextY = (y + blizzard.dy).mod(maxHeight)
                        computeIfAbsent(Position(nextX, nextY)) { mutableListOf() } += blizzard
                    }
                }
            }.let { copy(blizzards = it) }

        override fun toString(): String = buildString((maxWidth + 2) * (maxHeight + 2) + maxHeight + 1) {
            val firstAndLastLine: String = String(CharArray(maxWidth + 2) { WALL })
            appendLine(firstAndLastLine)
            for (y in 0 until maxHeight) {
                append(WALL)
                for (x in 0 until maxWidth) {
                    val blizzard = blizzards[Position(x, y)]
                    when {
                        blizzard.isNullOrEmpty() -> append(NONE)
                        blizzard.size == 1 -> append(blizzard.first().char)
                        else -> append(blizzard.size)
                    }
                }
                appendLine(WALL)
            }
            append(firstAndLastLine)
        }

        operator fun contains(position: Position): Boolean = position in blizzards
    }

    data class Position(val x: Int, val y: Int) {
        fun nextPositions(): List<Position> =
            listOf(
                copy(x = x + 1),
                copy(x = x - 1),
                copy(y = y - 1),
                copy(y = y + 1),
                this
            )
    }

    enum class Blizzard(val char: Char, val dx: Int, val dy: Int) {
        UP('^', 0, -1), DOWN('v', 0, 1), LEFT('<', -1, 0), RIGHT('>', 1, 0);

        companion object {
            fun of(char: Char): Blizzard = when (char) {
                UP.char -> UP
                DOWN.char -> DOWN
                LEFT.char -> LEFT
                RIGHT.char -> RIGHT
                else -> throw IllegalArgumentException()
            }
        }
    }
}