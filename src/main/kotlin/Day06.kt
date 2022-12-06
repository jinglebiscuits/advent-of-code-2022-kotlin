import java.io.File

class Day06 {

    init {
        File(javaClass.getResource("inputs/day6input.txt").toURI()).readLines().map {
            println("part1")
            decodeSignal(it, 4) // part1
            println("part2")
            decodeSignal(it, 14) //part2
        }
    }

    private fun decodeSignal(line: String, distinctCharactersNeeded: Int) {
        var startOfPacketMarker = mutableListOf<Char>()
        var count = 0
        var markerFound = false
        line.forEach { char ->
            if (markerFound) return@forEach
            count += 1
            if (char !in startOfPacketMarker) {
                startOfPacketMarker.add(char)
                if (startOfPacketMarker.size == distinctCharactersNeeded) {
                    markerFound = true
                }
            } else {
                startOfPacketMarker.add(char)
                startOfPacketMarker = startOfPacketMarker.subList(startOfPacketMarker.indexOfFirst {
                    it == char
                } + 1, startOfPacketMarker.size)
            }
        }
        println(count)
    }

}