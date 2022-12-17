import common.Input

typealias Crate = Char
typealias Stack = ArrayDeque<Crate>
typealias CrateMover = Day05.Instruction.(Day05.Cargo) -> Unit

object Day05 : Day<String, String>() {

    override fun part1(input: Input): String =
        input.useContentLines { findTopLineOfCargoAfterInstructions(it, CrateMover9000) }

    override fun part2(input: Input): String =
        input.useContentLines { findTopLineOfCargoAfterInstructions(it, CrateMover9001) }

    private fun findTopLineOfCargoAfterInstructions(sequence: Sequence<String>, crateMover: CrateMover): String {
        val iterator: Iterator<String> = sequence.iterator()
        val cargo = Cargo()
        while (iterator.hasNext()) {
            if (!cargo.addLine(iterator.next())) {
                break
            }
        }
        iterator.next()
        iterator.forEachRemaining { line -> Instruction.of(line).crateMover(cargo) }
        return cargo.topLine
    }

    private val CrateMover9000: CrateMover = { cargo -> invoke(cargo, false) }

    private val CrateMover9001: CrateMover = { cargo -> invoke(cargo, true) }

    class Cargo {

        private val stacks = List<Stack>(10) { ArrayDeque() }

        val topLine
            get() = buildString(stacks.size) {
                for (stack in stacks) {
                    if (stack.isNotEmpty()) {
                        append(stack.last())
                    }
                }
            }

        operator fun get(idx: Int): Stack = stacks[idx]

        fun addLine(line: String): Boolean {
            var idx = 0
            var added = false
            while (idx < line.length) {
                if (line[idx] == '[') {
                    val stackIdx = idx / 4
                    idx++
                    stacks[stackIdx].addFirst(line[idx])
                    added = true
                }
                idx++
            }
            return added
        }

    }

    data class Instruction(
        val cratesNumber: Int,
        val sourceStackIdx: Int,
        val targetStackIdx: Int
    ) {

        companion object {
            private val INSTRUCTION_REGEX = Regex("^move (\\d+) from (\\d+) to (\\d+)$")

            fun of(line: String): Instruction =
                INSTRUCTION_REGEX.find(line)!!
                    .groupValues
                    .let { Instruction(it[1].toInt(), it[2].toInt() - 1, it[3].toInt() - 1) }

        }

        operator fun invoke(cargo: Cargo, keepOrder: Boolean) {
            val sourceStack = cargo[sourceStackIdx]
            val targetStack = cargo[targetStackIdx]
            var tmpStack = List(cratesNumber) { _ -> sourceStack.removeLast() }
            if (keepOrder) {
                tmpStack = tmpStack.asReversed()
            }
            tmpStack.forEach { crate -> targetStack.addLast(crate) }
        }
    }
}