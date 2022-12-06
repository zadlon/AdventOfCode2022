object Day06 : Day<Int, Int>() {

    override fun part1(input: Input): Int = countCharactersBeforeMarkerSkipping(input.contentText.trim(), 4)

    override fun part2(input: Input): Int = countCharactersBeforeMarkerSkipping(input.contentText.trim(), 14)

    private fun countCharactersBeforeMarkerKt(string: String, windowSize: Int): Int =
        string
            .windowedSequence(windowSize) { it.toSet() }
            .indexOfFirst { it.size == windowSize }
            .let { it + windowSize }

    private fun countCharactersBeforeMarkerSkipping(string: String, windowSize: Int): Int {
        val maxStart = string.length + windowSize
        var start = 0
        val seen = IntArray(26) { _ -> -1 }
        mainLoop@ while (start < maxStart) {
            for (pos in start until start + windowSize) {
                val seenIdx = string[pos] - 'a'
                val previousSeenPos = seen[seenIdx]
                seen[seenIdx] = pos
                if (previousSeenPos in 0 until pos) {
                    start = previousSeenPos + 1
                    continue@mainLoop
                }
            }
            return start + windowSize
        }
        return -1
    }
}