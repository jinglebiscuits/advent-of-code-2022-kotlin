import java.io.File

class Day05 {

    val stacks: ArrayDeque<ArrayDeque<Char>> = ArrayDeque()

    init {
        File(javaClass.getResource("inputs/day5input.txt").toURI()).readLines().map {
            println(it)
            if (it.trim().length > 1 && it.trim()[0] == '[') {
                processCratePositionLine(it)
            } else if (it.startsWith("move")) {
                processRearrangementProcedure(it)
            }
        }
        println(stacks)
        var answer = ""
        for (deque in stacks) {
            answer += deque.last()
        }
        println("answer = $answer")
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

    private fun processRearrangementProcedure(line: String) {
        val procedure = line.split(" ").filter { it.toIntOrNull() != null }
        for (i in 0 until procedure[0].toInt()) {
            val crate = stacks[procedure[1].toInt() - 1].removeLast()
            stacks[procedure[2].toInt() - 1].addLast(crate)
        }
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