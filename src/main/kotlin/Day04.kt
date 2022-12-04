import java.io.File

class Day04 {

    private var fullyContainedRangeCount = 0
    private var overlappedRangesCount = 0

    init {
        File(javaClass.getResource("inputs/day4input.txt").toURI()).readLines().map {
            val elfPair = it.split(",")
            val elfPair1 = elfPair[0].split("-")
            val elfAssignment1 = ElfAssignment(elfPair1[0].toInt(), elfPair1[1].toInt())
            val elfPair2 = elfPair[1].split("-")
            val elfAssignment2 = ElfAssignment(elfPair2[0].toInt(), elfPair2[1].toInt())
            part2(elfAssignment1, elfAssignment2)
        }
    }

    private fun part1(elfAssignment1: ElfAssignment, elfAssignment2: ElfAssignment) {
        if (rangeFullyContained(elfAssignment1, elfAssignment2)) {
            fullyContainedRangeCount ++
        }
        println(fullyContainedRangeCount)
    }

    private fun part2(elfAssignment1: ElfAssignment, elfAssignment2: ElfAssignment) {
        if (rangesOverlap(elfAssignment1, elfAssignment2)) {
            overlappedRangesCount ++
        }
        println(overlappedRangesCount)
    }

    private fun rangeFullyContained(elfAssignment1: ElfAssignment, elfAssignment2: ElfAssignment) : Boolean {
        val minDiff = elfAssignment1.min - elfAssignment2.min
        val maxDiff = elfAssignment1.max - elfAssignment2.max
        return minDiff * maxDiff < 0 || minDiff * maxDiff == 0
    }

    private fun rangesOverlap(elfAssignment1: ElfAssignment, elfAssignment2: ElfAssignment) : Boolean {
        val bottomMin = elfAssignment2.min - elfAssignment1.min
        val bottomMax = elfAssignment2.max - elfAssignment1.min
        val topMin = elfAssignment2.min - elfAssignment1.max
        val topMax = elfAssignment2.max - elfAssignment1.max

        return (bottomMin * bottomMax <= 0 || topMin * topMax <= 0 || rangeFullyContained(elfAssignment1, elfAssignment2))
    }

    data class ElfAssignment(val min: Int, val max: Int)
}