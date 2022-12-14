import common.Input
import common.Position
import common.surroundingPositions
import java.util.LinkedList

typealias Elevation = Char
typealias StartPositionIdentifier = (Elevation) -> Boolean

object Day12 : Day<Int, Int>() {

    private const val START = 'S'
    private const val END = 'E'

    override fun part1(input: Input): Int = solve(input) { elevation -> elevation == START }

    override fun part2(input: Input): Int = solve(input) { elevation -> elevation == START || elevation == 'a' }

    private fun solve(input: Input, startPositionIdentifier: StartPositionIdentifier): Int {
        class GraphNodeWithSteps(
            private val node: GraphNode,
            val steps: Int
        ) {
            val vertices get() = node.vertices
            val isEnd get() = node.isEnd
        }

        fun GraphNode.withSteps(steps: Int): GraphNodeWithSteps =
            GraphNodeWithSteps(this, steps).also { this.isVisited = true }

        val q = parse(input.contentLines, startPositionIdentifier)
            .map { node -> node.withSteps(0) }
            .let { LinkedList(it) }

        while (q.isNotEmpty()) {
            val nodeWithSteps = q.poll()
            for (vertex in nodeWithSteps.vertices) {
                if (!vertex.isVisited) {
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
        val isEnd: Boolean,
        var isVisited: Boolean = false
    )

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
                nodes.surroundingPositions(Position(x, y))
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

}