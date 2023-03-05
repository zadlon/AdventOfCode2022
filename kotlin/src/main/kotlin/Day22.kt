import common.Input

typealias Board = List<CharArray>

object Day22 : Day<Long, Long>() {

    private const val WALL = '#'
    private const val VOID = ' '

    override fun part1(input: Input): Long {
        val (board, instructions) = parse(input)
        return instructions.fold(board.startPosition) { position, instruction -> instruction(position, board) }.score
    }

    override fun part2(input: Input): Long = 0L

    private fun parse(input: Input): Pair<Board, List<Instruction>> {
        val lines = input.contentLines
        val rows = lines.subList(0, lines.lastIndex - 1)
        val width = rows.maxOf { it.length }
        val board = rows.map { row -> row.padEnd(width, VOID).toCharArray() }
        return board to Instruction.of(lines.last())
    }

    private operator fun Board.get(position: Position): Char = with(position) { get(y)[x] }

    private val Board.startPosition get(): Position = Position(first().indexOfFirst { it != VOID }, 0, Direction.RIGHT)

    private fun Board.nextPosition(position: Position): Position {
        var (x, y, direction) = position
        val dx = direction.dx
        val dy = direction.dy
        do {
            x = (x + dx).mod(first().size)
            y = (y + dy).mod(size)
        } while (get(y)[x] == VOID)
        return Position(x, y, direction)
    }

    data class Position(val x: Int, val y: Int, val direction: Direction) {
        val score get(): Long = 1000L * (y + 1) + 4L * (x + 1) + direction.score
    }

    sealed interface Instruction {

        companion object {

            private val INSTRUCTIONS_REGEX = "(\\d+)([RL])?".toRegex()

            fun of(line: String): List<Instruction> =
                buildList {
                    INSTRUCTIONS_REGEX.findAll(line)
                        .forEach {
                            val group = it.groupValues
                            repeat(group[1].toInt()) { add(Advance) }
                            group[2].takeUnless { res -> res.isEmpty() }
                                ?.let { turn -> if (turn == "R") ClockwiseTurn else CounterClockwiseTurn }
                                ?.let { instruction -> add(instruction) }
                        }
                }
        }

        operator fun invoke(position: Position, board: Board): Position

        object Advance : Instruction {
            override fun invoke(position: Position, board: Board): Position {
                val nextPosition = board.nextPosition(position)
                return when (board[nextPosition]) {
                    WALL -> position
                    else -> nextPosition
                }
            }

        }

        object CounterClockwiseTurn : Instruction {
            override fun invoke(position: Position, board: Board): Position {
                val nextDirection: Direction = when (position.direction) {
                    Direction.RIGHT -> Direction.UP
                    Direction.UP -> Direction.LEFT
                    Direction.LEFT -> Direction.DOWN
                    Direction.DOWN -> Direction.RIGHT
                }
                return position.copy(direction = nextDirection)
            }
        }

        object ClockwiseTurn : Instruction {
            override fun invoke(position: Position, board: Board): Position {
                val nextDirection: Direction = when (position.direction) {
                    Direction.RIGHT -> Direction.DOWN
                    Direction.DOWN -> Direction.LEFT
                    Direction.LEFT -> Direction.UP
                    Direction.UP -> Direction.RIGHT
                }
                return position.copy(direction = nextDirection)
            }
        }
    }

    enum class Direction(val score: Int, val dx: Int, val dy: Int) {
        RIGHT(0, 1, 0), DOWN(1, 0, 1), LEFT(2, -1, 0), UP(3, 0, -1)
    }
}