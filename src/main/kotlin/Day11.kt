import java.util.LinkedList
import java.util.Queue

typealias WorryLevel = Long
typealias WorryLevelManagement = (WorryLevel) -> WorryLevel

object Day11 : Day<Long, Long>() {

    override fun part1(input: Input): Long {
        val monkeys = input.useContentLines { parseMonkeys(it) }
        return solve(monkeys, 20) { worryLevel -> worryLevel / 3 }
    }

    override fun part2(input: Input): Long {
        val monkeys = input.useContentLines { parseMonkeys(it) }
        val leastCommonMultiple = monkeys.map { it.divisor }.reduce(Long::times)
        return solve(monkeys, 10_000) { worryLevel -> worryLevel % leastCommonMultiple }
    }

    private fun parseMonkeys(sequence: Sequence<String>): List<Monkey> {
        return sequence
            .filter { line -> line.isNotBlank() }
            .map { line -> line.trim() }
            .chunked(6)
            .map { Monkey.of(it) }
            .toList()
    }

    inline fun solve(
        monkeys: List<Monkey>,
        numberOfTurns: Int,
        worryManagement: WorryLevelManagement
    ): Long {
        repeat(numberOfTurns) {
            for (monkey in monkeys) {
                while (monkey.hasNextItem()) {
                    val item = worryManagement(monkey.pollItem())
                    val nextMonkey = monkeys[monkey.nextMonkey(item)]
                    nextMonkey.receiveItem(item)
                }
            }
        }
        return monkeys.map { monkey -> monkey.counter }.sortedDescending().take(2).reduce(Long::times)
    }

    data class Monkey(
        val items: Queue<WorryLevel>,
        val operation: Operation,
        val divisor: Long,
        val trueMonkey: Int,
        val falseMonkey: Int,
        var counter: Long = 0
    ) {
        companion object {

            fun of(lines: List<String>): Monkey {
                val items = lines[1].removePrefix("Starting items:")
                    .split(",")
                    .map { item -> item.trim().toLong() }
                    .toCollection(LinkedList())
                val operation = Operation.of(lines[2])
                val divisor = lines[3].removePrefix("Test: divisible by ").toLong()
                val ifTrueMonkey = lines[4].removePrefix("If true: throw to monkey ").toInt()
                val ifFalseMonkey = lines[5].removePrefix("If false: throw to monkey ").toInt()
                return Monkey(
                    items,
                    operation,
                    divisor,
                    ifTrueMonkey,
                    ifFalseMonkey
                )
            }
        }

        fun hasNextItem(): Boolean = items.isNotEmpty()

        fun pollItem(): WorryLevel = operation(items.poll()).also { counter++ }

        fun nextMonkey(level: WorryLevel): Int = if (level % divisor == 0L) trueMonkey else falseMonkey

        fun receiveItem(level: WorryLevel) {
            items += level
        }
    }

    sealed interface Operation {

        operator fun invoke(self: WorryLevel): Long

        companion object {

            private val OPERATION_REGEX = Regex("^Operation: new = old ([+*]) (\\S+)$")

            fun of(line: String): Operation =
                OPERATION_REGEX.find(line)
                    ?.groupValues
                    ?.let { match ->
                        val operand = match[2]
                        when (match[1]) {
                            "+" -> if (operand == "old") PlusSelf else PlusValue(operand.toLong())
                            "*" -> if (operand == "old") TimesSelf else TimesValue(operand.toLong())
                            else -> throw IllegalArgumentException()
                        }
                    }
                    ?: throw IllegalArgumentException()
        }

        object PlusSelf : Operation {
            override fun invoke(self: WorryLevel): WorryLevel = self + self
        }

        data class PlusValue(val value: Long) : Operation {
            override fun invoke(self: WorryLevel): WorryLevel = self + value
        }

        object TimesSelf : Operation {
            override fun invoke(self: WorryLevel): WorryLevel = self * self
        }

        data class TimesValue(val value: Long) : Operation {
            override fun invoke(self: WorryLevel): WorryLevel = self * value
        }

    }
}