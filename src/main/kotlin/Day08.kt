typealias Tree = Int
typealias Forest = List<List<Tree>>
typealias Direction = List<Tree>
typealias TreeViews = List<Direction>
typealias TreeViewsFolder<T> = TreeViews.(Tree) -> T

object Day08 : Day<Int, Int>() {

    override fun part1(input: Input): Int =
        input.useContentLines { readForest(it) }
            .solve(
                init = 0,
                accumulator = Int::plus,
            ) { currentTree ->
                val visible = any { direction -> direction.all { tree -> tree < currentTree } }
                if (visible) 1 else 0
            }

    override fun part2(input: Input): Int =
        input.useContentLines { readForest(it) }
            .solve(
                init = 0,
                accumulator = Math::max
            ) { currentTree ->
                map { direction ->
                    val idx = direction.indexOfFirst { tree -> tree >= currentTree }
                    if (idx < 0) direction.size else idx + 1
                }.reduce(Int::times)
            }

    private fun readForest(sequence: Sequence<String>): Forest =
        sequence
            .map { line -> line.map { char -> char - '0' } }
            .toList()

    private inline fun Forest.solve(
        init: Int = 0,
        accumulator: (Int, Int) -> Int,
        folder: TreeViewsFolder<Int>
    ): Int {
        val maxWidth = first().size
        var acc = init
        for (i in indices) {
            for (j in 0 until maxWidth) {
                val currentTree = this[i][j]
                val result = getFourDirections(i, j)
                    .folder(currentTree)
                acc = accumulator(acc, result)
            }
        }
        return acc
    }

    private fun Forest.getFourDirections(height: Int, width: Int): TreeViews {
        val line = this[height]
        return listOf(
            line.subList(0, width).asReversed(),
            line.subList(width + 1, line.size),
            subList(0, height).map { it[width] }.asReversed(),
            subList(height + 1, size).map { it[width] }
        )
    }
}
