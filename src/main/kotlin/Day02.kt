typealias Round = CharArray
typealias RoundScoreDecoder = (Round) -> Int

object Day02 : Day<Int, Int>() {

    override fun part1(input: Input): Int = input.useContentLines { computeScore(it, CodeAsShapeReader) }

    override fun part2(input: Input): Int = input.useContentLines { computeScore(it, CodeAsOutcomeReader) }

    private fun computeScore(sequence: Sequence<String>, decoder: RoundScoreDecoder): Int =
        sequence
            .map { line -> readLine(line) }
            .map { round -> decoder(round) }
            .sum()

    private fun readLine(string: String): Round {
        var idx = 1
        while (idx < string.length && string[idx].isWhitespace()) {
            idx++
        }
        return charArrayOf(string[0], string[idx])
    }

    object CodeAsShapeReader : RoundScoreDecoder {

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

    object CodeAsOutcomeReader : RoundScoreDecoder {

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