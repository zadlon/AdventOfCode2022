object Day14 : Day<Int, Int>() {

    private val SAND_START_POSITION = Position(500, 0)

    override fun part1(input: Input): Int =
        parse(input)
            .countWhile { position -> position.y < maxDepth }

    override fun part2(input: Input): Int =
        parse(input)
            .let { cave -> cave.copy(maxDepth = cave.maxDepth + 1) }
            .countWhile { position -> position != SAND_START_POSITION }
            .let { result -> result + 1 }

    data class Cave(
        val positions: MutableSet<Position>,
        val maxDepth: Int
    ) {

        companion object {
            fun of(positions: MutableSet<Position>): Cave =
                Cave(
                    positions,
                    positions.maxOf { position -> position.y }
                )
        }

        inline fun countWhile(condition: Cave.(Position) -> Boolean): Int {
            var i = 0
            while (this.condition(nextSand())) {
                i++
            }
            return i
        }

        @PublishedApi
        internal fun nextSand(): Position {
            var current = SAND_START_POSITION
            while (current.y < maxDepth) {
                current = when {
                    current.down !in this -> current.down
                    current.left !in this -> current.left
                    current.right !in this -> current.right
                    else -> break
                }
            }
            add(current)
            return current
        }

        operator fun contains(position: Position): Boolean = position in positions

        private fun add(position: Position): Boolean = positions.add(position)
    }


    private fun parse(input: Input): Cave =
        input.useContentLines { lines ->
            lines
                .flatMap { line -> parse(line) }
                .toMutableSet()
                .let { positions -> Cave.of(positions) }
        }

    private fun parse(string: String): List<Position> =
        string
            .split(" -> ")
            .map { coordinates -> Position.of(coordinates) }
            .zipWithNext { p1, p2 -> p1.drawLine(p2) }
            .flatten()


    data class Position(val x: Int, val y: Int) {

        companion object {
            fun of(string: String): Position {
                val coordinates = string.split(',')
                return Position(coordinates[0].toInt(), coordinates[1].toInt())
            }
        }

        val left: Position get() = Position(x - 1, y + 1)

        val right: Position get() = Position(x + 1, y + 1)

        val down: Position get() = Position(x, y + 1)

        fun drawLine(other: Position): List<Position> =
            when {
                x > other.x || y > other.y -> other.drawLine(this)
                x == other.x -> (y..other.y).map { Position(x, it) }
                else -> (x..other.x).map { Position(it, y) }
            }
    }

}