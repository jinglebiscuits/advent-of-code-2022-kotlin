import java.io.File

class Day04 {

    private var fullyContainedRangeCount = 0

    init {
        File(javaClass.getResource("inputs/day4input.txt").toURI()).readLines().map {
            part1(it)
        }
    }

    private fun part1(line: String) {
        val elfPair = line.split(",")
        val elfPair1 = elfPair[0].split("-")
        val elfAssignment1 = ElfAssignment(elfPair1[0].toInt(), elfPair1[1].toInt())
        val elfPair2 = elfPair[1].split("-")
        val elfAssignment2 = ElfAssignment(elfPair2[0].toInt(), elfPair2[1].toInt())
        if (rangeFullyContained(elfAssignment1, elfAssignment2)) {
            fullyContainedRangeCount ++
        }
        println(fullyContainedRangeCount)
    }

    private fun part2(line: String) {

    }

    private fun rangeFullyContained(elfAssignment1: ElfAssignment, elfAssignment2: ElfAssignment) : Boolean {
        val minDiff = elfAssignment1.min - elfAssignment2.min
        val maxDiff = elfAssignment1.max - elfAssignment2.max
        return minDiff * maxDiff < 0 || minDiff * maxDiff == 0
    }

    data class ElfAssignment(val min: Int, val max: Int)
}