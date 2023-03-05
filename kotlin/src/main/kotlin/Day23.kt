import common.Input

object Day23 : Day<Int, Int>() {

    private const val OCCUPIED = '#'
    private const val FREE = '.'

    override fun part1(input: Input): Int {
        var elves = parse(input)
        var direction = Direction.NORTH
        repeat(10) {
            elves = doRound(elves, direction)
            direction = direction.nextDirection()
        }
        return elves.freeTiles
    }

    override fun part2(input: Input): Int {
        var elves = parse(input)
        var direction = Direction.NORTH
        var round = 0
        while (true) {
            round ++
            val newElves = doRound(elves, direction)
            if (newElves == elves) {
                break
            }
            elves = newElves
            direction = direction.nextDirection()
        }
        return round
    }


    private fun parse(input: Input): Set<Position> = input.useContentLines { lines ->
        lines.flatMapIndexed { y, line ->
            buildList { line.forEachIndexed { x, c -> if (c == OCCUPIED) add(Position(x, y)) } }
        }.toSet()
    }

    private fun doRound(elves: Set<Position>, firstDirection: Direction): Set<Position> {
        val canOrCannotProposeElves = elves.groupBy { elf -> elf.allAdjacentPositions.any { p -> p in elves } }
        val proposingElves = canOrCannotProposeElves[true]
        if (proposingElves.isNullOrEmpty()) {
            return elves
        }
        val cannotMove = mutableListOf<Position>()
        val propositions = mutableMapOf<Position, MutableList<Position>>()
        proposingElves.forEach { elf ->
            var direction = firstDirection
            var proposed = false
            do {
                if (elf.allPositionsInDirection(direction).none { p -> p in elves }) {
                    propositions.computeIfAbsent(elf.move(direction)) { mutableListOf() } += elf
                    proposed = true
                    break
                }
                direction = direction.nextDirection()
            } while (direction != firstDirection)
            if (!proposed) {
                cannotMove += elf
            }
        }
        val validPropositions = propositions.filterValues { proposing ->
            if (proposing.size == 1) {
                true
            } else {
                cannotMove += proposing
                false
            }
        }

        if (validPropositions.isEmpty()) {
            return elves
        }
        return ((canOrCannotProposeElves[false] ?: emptyList()) + cannotMove).toSet() + validPropositions.keys
    }


    private val Set<Position>.maxY get(): Int = maxOf { it.y }

    private val Set<Position>.minY get(): Int = minOf { it.y }

    private val Set<Position>.maxX get(): Int = maxOf { it.x }

    private val Set<Position>.minX get(): Int = minOf { it.x }

    private val Set<Position>.width get(): Int = maxX - minX + 1

    private val Set<Position>.height get(): Int = maxY - minY + 1

    private val Set<Position>.area get(): Int = height * width

    private val Set<Position>.freeTiles get(): Int = area - size

    private fun Set<Position>.lines(): Sequence<String> =
        generateSequence(minY) { it + 1 }
            .map { y ->
                buildString(width) {
                    for (x in minX..maxX) {
                        val char = when {
                            Position(x, y) in this@lines -> OCCUPIED
                            else -> FREE
                        }
                        append(char)
                    }
                }
            }
            .take(height)

    data class Position(val x: Int, val y: Int) {
        fun move(direction: Direction) = when (direction) {
            Direction.NORTH -> copy(y = y - 1)
            Direction.SOUTH -> copy(y = y + 1)
            Direction.WEST -> copy(x = x - 1)
            Direction.EAST -> copy(x = x + 1)
        }

        val allAdjacentPositions
            get(): List<Position> =
                listOf(
                    copy(x = x + 1),
                    copy(x = x - 1),
                    copy(y = y + 1),
                    copy(y = y - 1),
                    Position(x + 1, y + 1),
                    Position(x + 1, y - 1),
                    Position(x - 1, y + 1),
                    Position(x - 1, y - 1)
                )

        fun allPositionsInDirection(direction: Direction): List<Position> = when (direction) {
            Direction.NORTH -> listOf(Position(x + 1, y - 1), copy(y = y - 1), Position(x - 1, y - 1))
            Direction.SOUTH -> listOf(Position(x + 1, y + 1), copy(y = y + 1), Position(x - 1, y + 1))
            Direction.WEST -> listOf(Position(x - 1, y - 1), copy(x = x - 1), Position(x - 1, y + 1))
            Direction.EAST -> listOf(Position(x + 1, y - 1), copy(x = x + 1), Position(x + 1, y + 1))
        }
    }

    enum class Direction {
        NORTH, SOUTH, WEST, EAST;

        fun nextDirection(): Direction = when (this) {
            NORTH -> SOUTH
            SOUTH -> WEST
            WEST -> EAST
            EAST -> NORTH
        }
    }
}