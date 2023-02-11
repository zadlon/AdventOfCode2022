package common

enum class Direction {
    RIGHT, UP, LEFT, DOWN;

    companion object {
        fun of(c: Char): Direction =
            when (c) {
                'R' -> RIGHT
                'U' -> UP
                'L' -> LEFT
                'D' -> DOWN
                else -> throw IllegalArgumentException("Unrecognized direction: $c")
            }
    }

    fun move(position: Position): Position =
        when(this) {
            RIGHT -> position.right()
            UP -> position.up()
            LEFT -> position.left()
            DOWN -> position.down()
        }
}