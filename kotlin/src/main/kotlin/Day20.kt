import common.Input

object Day20 : Day<Long, Long>() {

    override fun part1(input: Input): Long = solve(input, 1, 1)

    override fun part2(input: Input): Long = solve(input, 811589153L, 10)

    private fun solve(input: Input, multiplier: Long, times: Int): Long {
        val mixedList = MixedList.of(input, multiplier)
        repeat(times) {
            mixedList.decryptOneRound()
        }
        val zeroNode = mixedList.find(0)!!
        return listOf(1000, 2000, 3000).map { zeroNode.skip(it) }.sumOf { it.value }
    }

    class MixedList(ints: List<Int>, multiplier: Long) {

        companion object {

            fun of(input: Input, multiplier: Long): MixedList =
                MixedList(input.contentLines.map { it.toInt() }, multiplier)

        }

        private val start: Node
        private val size: Int

        init {
            size = ints.size
            start = Node(multiplier * ints[0], null, null, null)
            var current = start
            for (i in 1 until ints.size) {
                val node = Node(multiplier * ints[i], null, current, null)
                current.originNext = node
                current.mixedNext = node
                current = node
            }
            current.originNext = start
            current.mixedNext = start
            start.mixedPrevious = current
        }

        data class Node(
            val value: Long,
            var originNext: Node?,
            var mixedPrevious: Node?,
            var mixedNext: Node?
        ) {

            fun remove() {
                mixedNext!!.mixedPrevious = mixedPrevious
                mixedPrevious!!.mixedNext = mixedNext
            }

            fun insertInFrontOf(other: Node) {
                val otherPrevious = other.mixedPrevious!!
                otherPrevious.mixedNext = this
                mixedPrevious = otherPrevious
                other.mixedPrevious = this
                mixedNext = other
            }

            fun skip(n: Int): Node {
                var current = this
                repeat(n) { current = current.mixedNext!! }
                return current
            }
        }

        fun decryptOneRound() {
            var current = start
            repeat(size) {
                moveNode(current)
                current = current.originNext!!
            }
        }

        private fun moveNode(node: Node) {
            val nextNode = when(val n = node.value.mod(size - 1)) {
                0 -> return
                else -> node.skip(n + 1)
            }
            node.remove()
            node.insertInFrontOf(nextNode)
        }

        fun find(value: Long): Node? {
            var current = start
            repeat(size) {
                if (current.value == value) {
                    return current
                }
                current = current.originNext!!
            }
            return null
        }
    }
}
