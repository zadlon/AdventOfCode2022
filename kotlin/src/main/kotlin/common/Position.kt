package common

data class Position(val x: Int, val y: Int) {

    fun up(): Position = copy(y = y - 1)

    fun down(): Position = copy(y = y + 1)

    fun left(): Position = copy(x = x - 1)

    fun right(): Position = copy(x = x + 1)

    fun upLeft(): Position = Position(x - 1, y - 1)

    fun upRight(): Position = Position(x + 1, y - 1)

    fun downLeft(): Position = Position(x - 1, y + 1)

    fun downRight(): Position = Position(x + 1, y + 1)
}

fun <T> Array<Array<T>>.getOrNull(position: Position): T? = getOrNull(position.y)?.getOrNull(position.x)

fun <T> Array<Array<T>>.surroundingPositions(position: Position): List<T & Any> =
    listOfNotNull(
        getOrNull(position.up()),
        getOrNull(position.right()),
        getOrNull(position.down()),
        getOrNull(position.left())
    )
