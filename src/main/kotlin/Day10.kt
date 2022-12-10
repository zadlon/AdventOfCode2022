object Day10 : Day<Int, String>() {

    override fun part1(input: Input): Int {
        return input.useContentLines { lines ->
            lines
                .toCpuCycles()
                .filter { cpu -> ((cpu.cycle - 20) % 40) == 0 }
                .sumOf { cpu -> with(cpu) { cycle * register } }
        }
    }

    private const val LIT = '#'
    private const val DARK = '.'

    override fun part2(input: Input): String {
        fun CPU.pixelState(pixel: Int): Char = if (pixel in (register - 1)..(register + 1)) LIT else DARK
        return input.useContentLines { lines ->
            lines
                .toCpuCycles()
                .chunked(40)
                .take(6)
                .fold(StringBuilder(246)) { sb, cpus ->
                    val lineChars = cpus.foldIndexed(CharArray(cpus.size)) { pixel, chars, cpu ->
                        chars[pixel] = cpu.pixelState(pixel)
                        chars
                    }
                    sb.appendLine(lineChars)
                }
                .toString()
        }
    }

    private fun Sequence<String>.toCpuCycles(): Sequence<CPU> =
        flatMap { line -> Instruction.of(line) }
            .runningFold(CPU(1, 1)) { cpu, instruction -> instruction(cpu) }

    sealed class Instruction {

        companion object {
            fun of(line: String): List<Instruction> =
                when {
                    line == "noop" -> listOf(Noop)
                    line.startsWith("addx") -> listOf(Noop, AddX(line.substring(5).toInt()))
                    else -> throw IllegalArgumentException()
                }
        }

        abstract operator fun invoke(cpu: CPU): CPU

        object Noop : Instruction() {
            override operator fun invoke(cpu: CPU): CPU = cpu.copy(cycle = cpu.cycle + 1)
        }

        data class AddX(val x: Int) : Instruction() {
            override operator fun invoke(cpu: CPU): CPU =
                CPU(cycle = cpu.cycle + 1, register = cpu.register + x)
        }
    }

    data class CPU(val cycle: Int, val register: Int)

}