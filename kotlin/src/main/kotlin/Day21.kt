import common.Input

object Day21 : Day<Long, Long>() {

    const val ROOT = "root"
    const val HUMAN = "humn"

    override fun part1(input: Input): Long = Monkey.parse(input).yell()

    override fun part2(input: Input): Long {
        val root = Monkey.parse(input)
        check(root is Monkey.MathResult)
        val humanPath = root.findPathTo(HUMAN)
        var ptr = humanPath.lastIndex - 1
        var current = humanPath[ptr]
        var value = when (current) {
            root.left -> root.right.yell()
            else -> root.left.yell()
        }
        while (ptr > 0) {
            check(current is Monkey.MathResult)
            ptr--
            val next = humanPath[ptr]
            value = when (next) {
                current.left -> current.operation.retrieveLeft(value, current.right.yell())
                else -> current.operation.retrieveRight(value, current.left.yell())
            }
            current = next
        }
        return value
    }


    sealed class Monkey(val name: String) {

        companion object {

            private val OPERATION_REGEX = Regex("(\\w+) ([\\-+*/]) (\\w+)")

            fun parse(input: Input): Monkey {
                val monkeys = input.useContentLines { lines -> lines.map { Monkey.of(it) }.associateBy { it.name } }
                for (monkey in monkeys.values) {
                    if (monkey is MathResult) {
                        with(monkey) {
                            left = monkeys[leftName]!!
                            right = monkeys[rightName]!!
                        }
                    }
                }
                return monkeys[ROOT]!!
            }

            fun of(line: String): Monkey {
                val separatorIdx = line.indexOf(':')
                val name = line.substring(0, separatorIdx)
                val yell = line.substring(separatorIdx + 2)
                yell.toLongOrNull()?.let { return Number(name, it) }
                val (left, op, right) = OPERATION_REGEX.find(yell)!!.destructured
                return MathResult(name, left, right, Operation.of(op))
            }

        }

        abstract fun yell(): Long

        fun findPathTo(name: String): List<Monkey> =
            mutableListOf<Monkey>().apply { findPathTo(name, this) }

        abstract fun findPathTo(name: String, acc: MutableList<Monkey>): Boolean

        class Number(name: String, private val value: Long) : Monkey(name) {
            override fun yell(): Long = value
            override fun findPathTo(name: String, acc: MutableList<Monkey>): Boolean {
                if (this.name == name) {
                    acc += this
                    return true
                }
                return false
            }
        }

        class MathResult(
            name: String,
            val leftName: String,
            val rightName: String,
            val operation: Operation
        ) : Monkey(name) {

            lateinit var left: Monkey

            lateinit var right: Monkey

            override fun yell(): Long = operation(left.yell(), right.yell())

            override fun findPathTo(name: String, acc: MutableList<Monkey>): Boolean {
                val inPath = (this.name == name || this.left.findPathTo(name, acc) || this.right.findPathTo(name, acc))
                if (inPath) {
                    acc += this
                }
                return inPath
            }
        }

    }

    enum class Operation {

        PLUS, MINUS, TIMES, DIV;

        companion object {
            fun of(value: String): Operation =
                when (value) {
                    "+" -> PLUS
                    "-" -> MINUS
                    "*" -> TIMES
                    "/" -> DIV
                    else -> throw IllegalArgumentException()
                }
        }

        operator fun invoke(left: Long, right: Long): Long = when (this) {
            PLUS -> left + right
            MINUS -> left - right
            TIMES -> left * right
            DIV -> left / right
        }

        fun retrieveLeft(res: Long, right: Long): Long = when (this) {
            PLUS -> res - right
            MINUS -> res + right
            TIMES -> res / right
            DIV -> res * right
        }

        fun retrieveRight(res: Long, left: Long): Long = when (this) {
            PLUS -> res - left
            MINUS -> left - res
            TIMES -> res / left
            DIV -> left / res
        }
    }

}
