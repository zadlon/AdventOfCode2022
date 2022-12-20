import common.Input
import common.Position
import common.Direction
import common.circularWithIndexIterator

typealias Jet = Direction

object Day17 : Day<Long, Long>() {

    private const val CHAMBER_WIDTH = 7

    private val shapes = listOf(Shape.Minus, Shape.Plus, Shape.Corner, Shape.Pipe, Shape.Square)

    override fun part1(input: Input): Long = Cave(CHAMBER_WIDTH).solve(input.parseJets(), 2_022)

    override fun part2(input: Input): Long = Cave(CHAMBER_WIDTH).solve(input.parseJets(), 1_000_000_000_000)

    private fun Input.parseJets(): List<Jet> = contentText.map { char ->
        when (char) {
            '>' -> Jet.RIGHT
            '<' -> Jet.LEFT
            else -> throw IllegalArgumentException()
        }
    }

    sealed interface Shape {

        fun origin(topEdge: Int): Position

        fun withOrigin(topEdge: Int) = Rock(origin(topEdge), this)

        fun up(origin: Position): List<Position>

        fun right(origin: Position): List<Position>

        fun down(origin: Position): List<Position>

        fun left(origin: Position): List<Position>

        fun all(origin: Position): List<Position>

        fun mostLeft(origin: Position): Position

        fun mostRight(origin: Position): Position

        fun mostDown(origin: Position): Position

        object Minus : Shape {

            override fun origin(topEdge: Int): Position = Position(2, topEdge + 4)

            override fun up(origin: Position): List<Position> = with(origin) {
                listOf(
                    this,
                    copy(x = x + 1),
                    copy(x = x + 2),
                    copy(x = x + 3),
                )
            }

            override fun right(origin: Position): List<Position> = with(origin) { listOf(copy(x = x + 3)) }

            override fun down(origin: Position): List<Position> = up(origin)

            override fun left(origin: Position): List<Position> = listOf(origin)

            override fun all(origin: Position): List<Position> = up(origin)

            override fun mostLeft(origin: Position): Position = origin

            override fun mostRight(origin: Position): Position = with(origin) { copy(x = x + 3) }

            override fun mostDown(origin: Position): Position = origin
        }

        object Plus : Shape {
            override fun origin(topEdge: Int): Position = Position(2, topEdge + 5)

            override fun up(origin: Position): List<Position> =
                with(origin) {
                    listOf(
                        this,
                        Position(x + 1, y + 1),
                        copy(x = x + 2)
                    )
                }

            override fun right(origin: Position): List<Position> =
                with(origin) {
                    listOf(
                        Position(x + 1, y + 1),
                        copy(x = x + 2),
                        Position(x + 1, y - 1),
                    )
                }

            override fun down(origin: Position): List<Position> =
                with(origin) {
                    listOf(
                        this,
                        Position(x + 1, y - 1),
                        copy(x = x + 2)
                    )
                }

            override fun left(origin: Position): List<Position> =
                with(origin) {
                    listOf(
                        Position(x + 1, y + 1),
                        this,
                        Position(x + 1, y - 1)
                    )
                }

            override fun all(origin: Position): List<Position> =
                with(origin) {
                    listOf(
                        this,
                        copy(x = x + 1),
                        copy(x = x + 2),
                        Position(x + 1, y + 1),
                        Position(x + 1, y - 1)
                    )
                }

            override fun mostLeft(origin: Position): Position = origin

            override fun mostRight(origin: Position): Position = with(origin) { copy(x = x + 2) }

            override fun mostDown(origin: Position): Position = with(origin) { Position(x + 1, y - 1) }
        }

        object Corner : Shape {

            override fun origin(topEdge: Int): Position = Position(2, topEdge + 4)

            override fun up(origin: Position): List<Position> =
                with(origin) {
                    listOf(
                        this,
                        copy(x = x + 1),
                        Position(x + 2, y + 2)
                    )
                }

            override fun right(origin: Position): List<Position> = with(origin) {
                listOf(
                    copy(x = x + 2),
                    Position(x + 2, y + 1),
                    Position(x + 2, y + 2)
                )
            }

            override fun down(origin: Position): List<Position> = with(origin) {
                listOf(
                    this,
                    copy(x = x + 1),
                    copy(x = x + 2)
                )
            }

            override fun left(origin: Position): List<Position> = with(origin) {
                listOf(
                    this,
                    Position(x + 2, y + 1),
                    Position(x + 2, y + 2)
                )
            }

            override fun all(origin: Position): List<Position> = with(origin) {
                listOf(
                    this,
                    copy(x = x + 1),
                    copy(x = x + 2),
                    Position(x + 2, y + 1),
                    Position(x + 2, y + 2)
                )
            }

            override fun mostLeft(origin: Position): Position = origin

            override fun mostRight(origin: Position): Position = with(origin) { copy(x = x + 2) }

            override fun mostDown(origin: Position): Position = origin
        }

        object Pipe : Shape {

            override fun origin(topEdge: Int): Position = Position(2, topEdge + 4)

            override fun up(origin: Position): List<Position> = with(origin) {
                listOf(
                    copy(y = y + 3)
                )
            }

            override fun right(origin: Position): List<Position> = with(origin) {
                listOf(
                    this,
                    copy(y = y + 1),
                    copy(y = y + 2),
                    copy(y = y + 3)
                )
            }

            override fun down(origin: Position): List<Position> = listOf(origin)

            override fun left(origin: Position): List<Position> = right(origin)

            override fun all(origin: Position): List<Position> = right(origin)

            override fun mostLeft(origin: Position): Position = origin

            override fun mostRight(origin: Position): Position = origin

            override fun mostDown(origin: Position): Position = origin
        }

        object Square : Shape {

            override fun origin(topEdge: Int): Position = Position(2, topEdge + 4)

            override fun up(origin: Position): List<Position> = with(origin) {
                listOf(
                    copy(y = y + 1),
                    Position(x + 1, y + 1)
                )
            }

            override fun right(origin: Position): List<Position> = with(origin) {
                listOf(
                    copy(x = x + 1),
                    Position(x + 1, y + 1)
                )
            }

            override fun down(origin: Position): List<Position> = with(origin) {
                listOf(
                    this,
                    copy(x = x + 1)
                )
            }

            override fun left(origin: Position): List<Position> = with(origin) {
                listOf(
                    this,
                    copy(y = y + 1)
                )
            }

            override fun all(origin: Position): List<Position> = with(origin) {
                listOf(
                    this,
                    copy(x = x + 1),
                    copy(y = y + 1),
                    Position(x + 1, y + 1)
                )
            }

            override fun mostLeft(origin: Position): Position = origin

            override fun mostRight(origin: Position): Position = with(origin) { copy(x = x + 1) }

            override fun mostDown(origin: Position): Position = origin
        }

    }

    data class Rock(
        var origin: Position,
        val shape: Shape
    ) {

        val up: List<Position> get() = shape.up(origin)

        val right: List<Position> get() = shape.right(origin)

        val mostRight: Position get() = shape.mostRight(origin)

        val left: List<Position> get() = shape.left(origin)

        val mostLeft: Position get() = shape.mostLeft(origin)

        val down: List<Position> get() = shape.down(origin)

        val mostDown: Position get() = shape.mostDown(origin)

        val all: List<Position> get() = shape.all(origin)
    }

    class Cave(private val width: Int) {

        private val blocked: MutableSet<Position> = mutableSetOf()
        private val topEdges = IntArray(width)
        private var topEdge: Int = 0
        private var topEdgesHash: Int = topEdges.contentHashCode()

        private val periodStartCache = mutableMapOf<PeriodStartKey, PeriodStartValue>()

        private data class PeriodStartKey(val shapeIdx: Int, val directionIdx: Int, val topLineSignature: Int)
        private data class PeriodStartValue(val counter: Long, val height: Int)


        fun solve(jets: List<Jet>, iterations: Long): Long {
            val shapeIterator = shapes.circularWithIndexIterator()
            val jetsIterator = jets.circularWithIndexIterator()
            var iteration = 0L
            var periodFound = false
            var repeatedPeriodHeight = 0L
            while (iteration < iterations) {
                val (shapeIdx, shape) = shapeIterator.next()
                val (jetIdx, jet) = jetsIterator.next()
                iteration++
                if (!periodFound) {
                    val patternKey = PeriodStartKey(shapeIdx, jetIdx, topEdgesHash)
                    periodStartCache.putIfAbsent(patternKey, PeriodStartValue(iteration, topEdge))
                        ?.let { (counterAtPeriodStart, topEdgeAtStart) ->
                            periodFound = true
                            val period = iteration - counterAtPeriodStart
                            val repetitions = (iterations - iteration) / period
                            repeatedPeriodHeight = (topEdge - topEdgeAtStart) * repetitions
                            iteration += repetitions * period
                            periodStartCache.clear()
                        }
                }
                val positionedShape = shape.withOrigin(topEdge)
                fall(positionedShape, jet, jetsIterator)
            }
            return topEdge + repeatedPeriodHeight
        }

        private fun fall(rockInit: Rock, jetInit: Jet, jets: Iterator<IndexedValue<Jet>>) {
            var rock = rockInit
            var jet = jetInit
            while (true) {
                rock = jet.pushOne(rock)
                val fallenRock = fallOne(rock)
                if (fallenRock == rock) {
                    add(fallenRock)
                    break
                }
                rock = fallenRock
                jet = jets.next().value
            }
        }

        private fun Jet.pushOne(rock: Rock): Rock =
            when (this) {
                Jet.LEFT -> {
                    val next = with(rock) { copy(origin = origin.copy(x = origin.x - 1)) }
                    if (next.mostLeft.x < 0 || next.left.any { it in this@Cave }) rock else next
                }

                Jet.RIGHT -> {
                    val next = with(rock) { copy(origin = origin.copy(x = origin.x + 1)) }
                    if (next.mostRight.x >= width || next.right.any { it in this@Cave }) rock else next
                }

                else -> throw IllegalArgumentException()

            }

        private fun fallOne(rock: Rock): Rock {
            val fallenRock = with(rock) { copy(origin = origin.copy(y = origin.y - 1)) }
            return if (fallenRock.mostDown.y == 0 || fallenRock.down.any { it in this }) rock else fallenRock
        }

        private fun add(rock: Rock) {
            blocked.addAll(rock.all)
            var changed = false
            for (position in rock.up) {
                with(position) {
                    if (topEdges[x] < y) {
                        topEdges[x] = y
                        changed = true
                    }
                }
            }
            if (changed) {
                topEdge = topEdges.max()
                topEdgesHash = topEdges.map { it - topEdge }.hashCode()
            }
        }

        operator fun contains(position: Position): Boolean = position in blocked
    }
}