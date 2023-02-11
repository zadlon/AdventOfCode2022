import common.Input
import common.Position
import kotlin.math.abs

object Day15 : Day<Int, Long>() {

    var part1Row = 2_000_000

    override fun part1(input: Input): Int {
        val zones = input.contentLines.map { Zone.of(it) }.sortedByDescending { it.radius }
        val xMin = zones.minOf { it.sensor.x - it.radius }
        val xMax = zones.maxOf { it.sensor.x + it.radius }
        var position = Position(xMin, part1Row)
        var counter = 0
        while (position.x <= xMax) {
            position = zones.firstOrNull { zone -> position in zone }
                ?.advanceRightUntilOutOfZone(position)
                ?.also { newPosition -> counter += (newPosition.x - position.x) }
                ?: position.copy(x = position.x + 1)
        }
        val beaconsOnLine = zones
            .map { zone -> zone.beacon }
            .filter { beacon -> beacon.y == part1Row }
            .distinctBy { beacon -> beacon.x }
            .count()
        return counter - beaconsOnLine
    }

    var part2CoordinatesMax = 4_000_000

    private const val MULTIPLIER = 4_000_000L

    override fun part2(input: Input): Long {
        val zones = input.contentLines.map { Zone.of(it) }.sortedByDescending { it.radius }
        var position = Position(-1, -1)
        mainLoop@ for (y in 0..part2CoordinatesMax) {
            position = Position(0, y)
            while (position.x <= part2CoordinatesMax) {
                position = zones.firstOrNull { zone -> position in zone }
                    ?.advanceRightUntilOutOfZone(position)
                    ?: break@mainLoop
            }
        }
        return with(position) { MULTIPLIER * x + y }
    }

    data class Zone(val sensor: Position, val beacon: Position) {

        companion object {

            private val ZONE_REGEX =
                Regex("^Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)$")

            fun of(string: String): Zone =
                ZONE_REGEX.find(string)
                    ?.groupValues
                    ?.let { Zone(Position(it[1].toInt(), it[2].toInt()), Position(it[3].toInt(), it[4].toInt())) }
                    ?: throw IllegalArgumentException()
        }

        val radius: Int = sensor.manhattanDistance(beacon)

        operator fun contains(position: Position): Boolean = sensor.manhattanDistance(position) <= radius

        fun advanceRightUntilOutOfZone(position: Position): Position {
            val diffX: Int = (radius - abs(position.y - sensor.y)) + 1
            return position.copy(x = sensor.x + diffX)
        }
    }

    fun Position.manhattanDistance(other: Position): Int = abs(x - other.x) + abs(y - other.y)

}
