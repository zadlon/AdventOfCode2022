import common.Input

object Day07 : Day<Int, Int>() {

    override fun part1(input: Input): Int {
        val root = input.useContentLines { Directory.parseTree(it) }
        return mutableListOf<Directory>()
            .also { list -> findAll(root, list) { it.size <= 100_000 } }
            .sumOf { it.size }
    }


    override fun part2(input: Input): Int {
        val root = input.useContentLines { Directory.parseTree(it) }
        val unusedSpace = 70_000_000 - root.size
        val spaceToFree = 30_000_000 - unusedSpace
        return mutableListOf<Directory>()
            .also { list -> findAll(root, list) { it.size >= spaceToFree } }
            .minOf { it.size }

    }

    private fun findAll(directory: Directory, acc: MutableList<Directory>, predicate: (Directory) -> Boolean) {
        if (predicate(directory)) {
            acc += directory
        }
        for (child in directory.children.values) {
            findAll(child, acc, predicate)
        }
    }

    class Directory(val name: String, val parent: Directory?) {

        companion object {

            fun parseTree(sequence: Sequence<String>): Directory {
                val root = Directory("/", null)
                var current = root
                for (line in sequence) {
                    if (line.startsWith('$')) {
                        val cmd = line.substring(2)
                        if (cmd.startsWith("cd")) {
                            val target = cmd.substring(3)
                            current = when (target) {
                                "/" -> root
                                ".." -> current.parent!!
                                else -> current.children[target]!!
                            }
                        }
                    } else if (line.startsWith('d')) {
                        Directory(line.substring(4), current)
                            .also { current.children[it.name] = it }
                    } else {
                        val idx = line.indexOf(' ')
                        current.sizeOfFiles += line.substring(0, idx).toInt()
                    }
                }
                return root
            }
        }

        val children = mutableMapOf<String, Directory>()

        private var sizeOfFiles = 0

        val size: Int
            get() = sizeOfFiles + children.values.sumOf { it.size }

    }
}