import java.util.HashSet
import java.util.LinkedList

typealias Elevation = Char
typealias StartPositionIdentifier = (Elevation) -> Boolean

object Day12 : Day<Int, Int>() {

    private const val START = 'S'
    private const val END = 'E'

    override fun part1(input: Input): Int = solve(input) { elevation -> elevation == START }

    override fun part2(input: Input): Int = solve(input) { elevation -> elevation == START || elevation == 'a' }

    private fun solve(input: Input, identifier: StartPositionIdentifier): Int =
        parse(input.contentLines, identifier)
            .map { start -> solveOne(start) }
            .filter { solution -> solution != -1 }
            .minOrNull() ?: -1

    private fun solveOne(start: GraphNode): Int {
        class GraphNodeWithSteps(
            private val node: GraphNode,
            val steps: Int
        ) {
            val vertices get() = node.vertices
            val isEnd get() = node.isEnd
        }
        fun GraphNode.withSteps(steps: Int): GraphNodeWithSteps = GraphNodeWithSteps(this, steps)

        val q = LinkedList<GraphNodeWithSteps>().apply { add(GraphNodeWithSteps(start, 0)) }
        val visited = HashSet<GraphNode>()
        while (q.isNotEmpty()) {
            val nodeWithSteps = q.poll()
            for (vertex in nodeWithSteps.vertices) {
                if (visited.add(vertex)) {
                    val newNodeWithSteps = vertex.withSteps(nodeWithSteps.steps + 1)
                    if (newNodeWithSteps.isEnd) {
                        return newNodeWithSteps.steps
                    }
                    q += newNodeWithSteps
                }
            }
        }
        return -1
    }

    class GraphNode(
        val vertices: MutableList<GraphNode> = mutableListOf(),
        val isEnd: Boolean
    ) {
        override fun hashCode(): Int = System.identityHashCode(this)

        override fun equals(other: Any?): Boolean = this === other
    }

    private fun Elevation.canClimb(other: Elevation): Boolean = (other.coerce() - coerce()) <= 1

    private fun Elevation.coerce(): Elevation =
        when (this) {
            START -> 'a'
            END -> 'z'
            else -> this
        }

    private fun parse(strings: List<String>, startPositionIdentifier: StartPositionIdentifier): List<GraphNode> {
        data class GraphNodeWithElevation(
            val node: GraphNode,
            val elevation: Elevation
        )
        fun GraphNode.withElevation(elevation: Elevation): GraphNodeWithElevation =
            GraphNodeWithElevation(this, elevation)

        val nodes: Array<Array<GraphNodeWithElevation?>> =
            Array(strings.size) { _ -> Array(strings.first().length) { _ -> null } }
        val startNodes = mutableListOf<GraphNode>()
        for (y in strings.indices) {
            val row = strings[y]
            for (x in row.indices) {
                val currentElevation = row[x]
                val currentNode = GraphNode(isEnd = currentElevation == END)
                nodes[y][x] = currentNode.withElevation(currentElevation)
                nodes.getAllSurroundingPositions(Position(x, y))
                    .mapNotNull { (x1, y1) -> nodes[y1][x1] }
                    .forEach { (otherNode, otherElevation) ->
                        if (currentElevation.canClimb(otherElevation)) {
                            currentNode.vertices += otherNode
                        }
                        if (otherElevation.canClimb(currentElevation)) {
                            otherNode.vertices += currentNode
                        }
                    }
                if (startPositionIdentifier(currentElevation)) {
                    startNodes += currentNode
                }
            }
        }
        return startNodes
    }

    private fun <T> Array<Array<T>>.getAllSurroundingPositions(position: Position): List<Position> {
        val res = mutableListOf<Position>()
        if (position.x > 0) {
            res += position.left()
        }
        if (position.x < first().size - 1) {
            res += position.right()
        }
        if (position.y > 0) {
            res += position.down()
        }
        if (position.y < size - 1) {
            res += position.up()
        }
        return res
    }

    data class Position(val x: Int, val y: Int) {

        fun up(): Position = copy(y = y + 1)

        fun down(): Position = copy(y = y - 1)

        fun right(): Position = copy(x = x + 1)

        fun left(): Position = copy(x = x - 1)
    }

}