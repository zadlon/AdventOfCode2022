import common.Input
import common.memoize

object Day16 : Day<Int, Int>() {

    override fun part1(input: Input): Int {
        val start = input.useContentLines { Valve.of(it) }
        var f: (Valve, Set<Valve>, Int) -> Int = { _, _, _ -> 0 }
        f = { valve, opened, remainingTime ->
            if (remainingTime == 0) {
                0
            } else {
                var res = -1
                val nextRemainingTime = remainingTime - 1
                if (valve !in opened && valve.rate > 0) {
                    res = nextRemainingTime * valve.rate + f(valve, opened + valve, nextRemainingTime)
                }
                maxOf(res, valve.tunnels.maxOf { f(it, opened, nextRemainingTime) })
            }
        }
        f = f.memoize()
        return f(start, setOf(), 30)
    }

    //TODO
    override fun part2(input: Input): Int = 0

    class Valve(
        val rate: Int,
        val tunnels: MutableList<Valve> = mutableListOf(),
    ) {

        companion object {

            private val REGEX = Regex("^Valve (\\w{2}) has flow rate=(\\d+); tunnels? leads? to valves? ([\\w{2}, ]+)$")

            fun of(strings: Sequence<String>): Valve {
                val parsed: Map<String, Pair<Valve, List<String>>> =
                    strings.map { REGEX.find(it)?.groupValues ?: throw IllegalArgumentException(it) }
                        .groupBy({ match -> match[1] }) { match -> Valve(match[2].toInt()) to match[3].split(", ") }
                        .mapValues { (_, v) -> v.first() }
                parsed.values.forEach { (valve, tunnels) ->
                    for (tunnel in tunnels) {
                        parsed[tunnel]!!.first.tunnels += valve
                    }
                }
                return parsed["AA"]!!.first
            }
        }
    }

}
