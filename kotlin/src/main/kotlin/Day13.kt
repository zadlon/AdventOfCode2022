import common.Input
import common.split
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive

object Day13 : Day<Int, Int>() {

    override fun part1(input: Input): Int =
        input.useContentLines { lines ->
            lines
                .split()
                .map { pair -> pair.map { Json.parseToJsonElement(it) } }
                .withIndex()
                .filter { (_, pair) -> pair[0] <= pair[1] }
                .sumOf { (idx, _) -> idx + 1 }
        }

    private val DIVIDER_PACKETS_1 = Json.parseToJsonElement("[[2]]")
    private val DIVIDER_PACKETS_2 = Json.parseToJsonElement("[[6]]")

    override fun part2(input: Input): Int =
        input.useContentLines { lines ->
            lines
                .filter { line -> line.isNotBlank() }
                .map { line -> Json.parseToJsonElement(line) }
                .toMutableList()
                .apply {
                    add(DIVIDER_PACKETS_1)
                    add(DIVIDER_PACKETS_2)
                    sortWith(JsonElementComparator)
                }
                .run {
                    val a = binarySearch(DIVIDER_PACKETS_1, JsonElementComparator) + 1
                    val b = binarySearch(DIVIDER_PACKETS_2, JsonElementComparator, fromIndex = a) + 1
                    a * b
                }
        }

    object JsonElementComparator : Comparator<JsonElement> {
        override fun compare(o1: JsonElement, o2: JsonElement): Int = o1.compareTo(o2)
    }

    operator fun JsonElement.compareTo(other: JsonElement): Int {
        if (this is JsonPrimitive && other is JsonPrimitive) {
            return this.toInt().compareTo(other.toInt())
        }
        val it = this.wrapIfNecessary().iterator()
        val otherIt = other.wrapIfNecessary().iterator()
        while (it.hasNext() && otherIt.hasNext()) {
            val res = it.next().compareTo(otherIt.next())
            if (res != 0) {
                return res
            }
        }
        return when {
            it.hasNext() -> 1
            otherIt.hasNext() -> -1
            else -> 0
        }
    }

    private fun JsonPrimitive.toInt() = content.toInt()

    private fun JsonElement.wrapIfNecessary(): JsonArray = if (this is JsonArray) this else JsonArray(listOf(this))
}