typealias Round = CharArray
typealias RoundScoreReader = (Round) -> Int

object Day2 {

    fun computeScore(sequence: Sequence<String>, reader: RoundScoreReader): Int =
        sequence.map { readLine(it) }
            .map { reader(it) }
            .sum()

    private fun readLine(string: String): Round {
        var idx = 1
        while (idx < string.length && string[idx].isWhitespace()) {
            idx++
        }
        return charArrayOf(string[0], string[idx])
    }

    object Part1 : RoundScoreReader {

        override fun invoke(p1: CharArray): Int {
            val opponent: Shape = Shape.of(p1[0])
            val mine: Shape = when (p1[1]) {
                'X' -> Shape.ROCK
                'Y' -> Shape.PAPER
                'Z' -> Shape.SCISSORS
                else -> throw IllegalArgumentException()
            }

            return mine.score + mine.outcome(opponent).score
        }

    }

    object Part2 : RoundScoreReader {

        override fun invoke(p1: CharArray): Int {
            val opponent: Shape = Shape.of(p1[0])
            val outcome = Outcome.of(p1[1])
            val mine = when (outcome) {
                Outcome.WIN -> opponent.losesOver
                Outcome.DRAW -> opponent
                Outcome.LOST -> opponent.winsOver
            }
            return outcome.score + mine.score
        }
    }

    enum class Shape(val score: Int) {
        ROCK(1),
        PAPER(2),
        SCISSORS(3);

        companion object {

            fun of(char: Char): Shape =
                when (char) {
                    'A' -> ROCK
                    'B' -> PAPER
                    'C' -> SCISSORS
                    else -> throw IllegalArgumentException()
                }

        }

        val winsOver: Shape
            get() = when (this) {
                ROCK -> SCISSORS
                PAPER -> ROCK
                SCISSORS -> PAPER
            }

        val losesOver: Shape
            get() = when (this) {
                ROCK -> PAPER
                PAPER -> SCISSORS
                SCISSORS -> ROCK
            }


        fun outcome(opponent: Shape): Outcome =
            when {
                this == opponent -> Outcome.DRAW
                winsOver == opponent -> Outcome.WIN
                else -> Outcome.LOST
            }
    }

    enum class Outcome(val score: Int) {
        WIN(6), DRAW(3), LOST(0);

        companion object {

            fun of(char: Char): Outcome =
                when (char) {
                    'X' -> LOST
                    'Y' -> DRAW
                    'Z' -> WIN
                    else -> throw IllegalArgumentException()
                }
        }
    }
}


fun main() {
    println("Part1 : ${Day2.useInputLines { Day2.computeScore(it, Day2.Part1) }}")
    println("Part2 : ${Day2.useInputLines { Day2.computeScore(it, Day2.Part2) }}")
}
