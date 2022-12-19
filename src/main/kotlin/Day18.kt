import common.Input
import java.util.LinkedList

object Day18 : Day<Int, Int>() {

    override fun part1(input: Input): Int = parseInput(input).visibleFaces

    override fun part2(input: Input): Int {
        return Box(parseInput(input)).insideVisibleFaces
    }

    private class Box(private val droplets: Collection<Position3D>) {

        private val xMin = droplets.minOf { it.x } - 1
        private val xMax = droplets.maxOf { it.x } + 1
        private val yMin = droplets.minOf { it.y } - 1
        private val yMax = droplets.maxOf { it.y } + 1
        private val zMin = droplets.minOf { it.z } - 1
        private val zMax = droplets.maxOf { it.z } + 1

        operator fun contains(position: Position3D): Boolean =
            with(position) { x in xMin..xMax && y in yMin..yMax && z in zMin..zMax }

        val air: Collection<Position3D>
            get() {
                val q = LinkedList<Position3D>()
                val maxVolume = height * width * length
                val air = HashSet<Position3D>(maxVolume.toExpectedCapacity())
                Position3D(xMin, yMin, zMin).let {
                    q += it
                    air += it
                }
                while (q.isNotEmpty()) {
                    val currentPosition = q.poll()
                    currentPosition.adjacentPositions.forEach { position ->
                        if (position in this && position !in droplets && position !in air) {
                            air += position
                            q += position
                        }
                    }
                }
                return air
            }

        val width: Int get() = xMax - xMin + 1

        val length: Int get() = yMax - yMin + 1

        val height: Int get() = zMax - zMin + 1

        val outsideVisibleFaces: Int get() = 2 * (width * length + width * height + length * height)

        val insideVisibleFaces: Int get() = air.visibleFaces - outsideVisibleFaces
    }

    private fun parseInput(input: Input) : Set<Position3D> =
        with(input.contentLines) { mapTo(HashSet(size.toExpectedCapacity())) { line -> Position3D.of(line) } }

    private val Iterable<Position3D>.visibleFaces: Int
        get() =
            sumOf { position -> position.adjacentPositions.count { a -> a !in this } }


    data class Position3D(val x: Int, val y: Int, val z: Int) {
        companion object {
            fun of(string: String): Position3D =
                string.split(',').map { it.toInt() }.let { (x, y, z) -> Position3D(x, y, z) }
        }

        val adjacentPositions: List<Position3D>
            get() = listOf(
                copy(x = x + 1), copy(x = x - 1), copy(y = y + 1), copy(y = y - 1), copy(z = z + 1), copy(z = z - 1)
            )
    }

    private fun Int.toExpectedCapacity(): Int = ((this / 0.75F) + 1).toInt()
}