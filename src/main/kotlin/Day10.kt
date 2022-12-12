import java.io.File

class Day10 {

    var cycle = 0
    var registerX = 1
    val keyCycles = mutableListOf<Int>()
    var part1Answer = 0
    val litPixelIndices = mutableListOf<Int>()

    init {
        for (i in 20..220 step 40) {
            keyCycles.add(i)
        }
        File(javaClass.getResource("inputs/day10input.txt").toURI()).readLines().map {
            println(it)
            processCommand(it)
        }
        println("part1 answer $part1Answer")
        visualizePart2()
    }

    private fun processCommand(command: String) {
        if (command == "noop") {
            bumpCycle()
        } else {
            bumpCycle()
            bumpCycle()
            registerX += command.split(" ")[1].toInt()
        }
    }

    private fun bumpCycle() {
        if (Math.abs(cycle % 40 - registerX) < 2) {
            println(cycle)
            litPixelIndices.add(cycle)
        }
        cycle += 1
        if (cycle in keyCycles) {
            part1Answer += cycle * registerX
        }
    }

    private fun visualizePart2() {
        var viz = ""
        for (i in 0..239) {
            if (i in litPixelIndices) {
                viz += "#"
            } else {
                viz += "."
            }
            if (i % 40 == 39) {
                viz += "\n"
            }
        }
        println(viz)
    }
}