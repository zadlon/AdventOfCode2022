import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive

object Day13 : Day<Int, Int>() {

    override fun part1(input: Input): Int =
        input.useContentLines { lines ->
            lines.group()
                .map { pair -> pair.map { Json.parseToJsonElement(it) } }
                .withIndex()
                .filter { (_, pair) -> pair[0] <= pair[1] }
                .sumOf { (idx, _) -> idx + 1 }
        }

    override fun part2(input: Input): Int =
        input.useContentLines {
            it.filter { line -> line.isNotBlank() }
                .map { line -> Json.parseToJsonElement(line) }
                .toMutableList()
                .apply { sortWith(JsonElementComparator) }
                .run {
                    val x2 = binarySearch(
                        element = JsonArray(listOf(JsonArray(listOf(JsonPrimitive(2))))),
                        comparator = JsonElementComparator
                    ).opposite()
                    val x6 = binarySearch(
                        element = JsonArray(listOf(JsonArray(listOf(JsonPrimitive(6))))),
                        comparator = JsonElementComparator,
                        fromIndex = x2
                    ).opposite() + 1

                    (x2) * (x6)
                }
        }

    private fun Int.opposite(): Int = -this

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