import java.io.File

class Day06 {

    init {
        File(javaClass.getResource("inputs/day6input.txt").toURI()).readLines().map {
            part1(it)
        }
    }

    private fun part1(line: String) {
        var startOfPacketMarker = mutableListOf<Char>()
        var count = 0
        var markerFound = false
        line.forEach { char ->
            if (markerFound) return@forEach
            count += 1
            if (char !in startOfPacketMarker) {
                startOfPacketMarker.add(char)
                if (startOfPacketMarker.size == 4) {
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