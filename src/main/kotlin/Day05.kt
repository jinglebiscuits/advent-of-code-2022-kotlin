import java.io.File

class Day05 {

    val stacks: ArrayDeque<ArrayDeque<Char>> = ArrayDeque()

    init {
        File(javaClass.getResource("inputs/day5input.txt").toURI()).readLines().map {
            part2(it)
        }
        var answer = ""
        for (deque in stacks) {
            answer += deque.last()
        }
        println("answer = $answer")
    }

    private fun part1(line: String) {
        if (line.trim().length > 1 && line.trim()[0] == '[') {
            processCratePositionLine(line)
        } else if (line.startsWith("move")) {
            processRearrangementProcedure9000(line)
        }
    }

    private fun part2(line: String) {
        if (line.trim().length > 1 && line.trim()[0] == '[') {
            processCratePositionLine(line)
        } else if (line.startsWith("move")) {
            processRearrangementProcedure9001(line)
        }
    }

    private fun processCratePositionLine(line: String) {
        var counter = 0
        line.chunked(4).forEach {
            if (it.isNotBlank()) {
                if (stacks.size < counter + 1) {
                    createStacks(counter - stacks.size)
                }
                stacks[counter].addFirst(getCharFromCrate(it))
            }
            counter ++
        }
    }

    private fun processRearrangementProcedure9000(line: String) {
        val procedure = line.split(" ").filter { it.toIntOrNull() != null }
        for (i in 0 until procedure[0].toInt()) {
            val crate = stacks[procedure[1].toInt() - 1].removeLast()
            stacks[procedure[2].toInt() - 1].addLast(crate)
        }
    }

    private fun processRearrangementProcedure9001(line: String) {
        val procedure = line.split(" ").filter { it.toIntOrNull() != null }
        val crates = mutableListOf<Char>()
        for (i in 0 until procedure[0].toInt()) {
            crates.add(0, stacks[procedure[1].toInt() - 1].removeLast())
        }
        stacks[procedure[2].toInt() - 1].addAll(crates)
    }

    private fun createStacks(numberOfStacks: Int) {
        for (i in 0 .. numberOfStacks) {
            stacks.addLast(ArrayDeque())
        }
    }

    private fun getCharFromCrate(crate: String) : Char {
        return crate.trim()[1]
    }
}