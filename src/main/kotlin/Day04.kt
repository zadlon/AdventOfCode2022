object Day04 : Day<Int, Int>() {

    override fun part1(input: Input): Int = input.useContentLines { countPairsWithFullyContainedSections(it) }

    override fun part2(input: Input): Int = input.useContentLines { countPairWithOverlappingSections(it) }

    private fun countPairsWithFullyContainedSections(sequence: Sequence<String>): Int =
        sequence
            .map { line -> toPairOfSections(line) }
            .filter { (first, second) -> first in second || second in first }
            .count()

    private fun countPairWithOverlappingSections(sequence: Sequence<String>): Int =
        sequence
            .map { line -> toPairOfSections(line) }
            .filter { (first, second) -> first.overlaps(second) }
            .count()

    private fun toPairOfSections(line: String): Pair<Section, Section> =
        line.split(',').let { Section.of(it[0]) to Section.of(it[1]) }

    data class Section(
        val start: Int,
        val end: Int
    ) {

        companion object {
            fun of(s: String): Section = s.split('-').let { Section(it[0].toInt(), it[1].toInt()) }
        }

        operator fun contains(other: Section): Boolean = start <= other.start && end >= other.end

        fun overlaps(other: Section): Boolean = start <= other.end && this.end >= other.start
    }

}