import java.io.File
import java.math.BigInteger
import kotlin.math.floor

class Day11 {

    val monkeys = mutableListOf<Monkey>()
    val rounds = 10000
    var lcm = 1L

    init {
        File(javaClass.getResource("inputs/day11input.txt").toURI()).readLines().map {
            println(it)
            if (it.startsWith("Monkey")) {
                monkeys.add(Monkey())
            } else if (it.trim().startsWith("Starting")) {
                it
                    .split(":")[1]
                    .split(", ")
                    .map { stringIt -> stringIt.trim().toLong() }
                    .forEach { intIt -> monkeys.last().startingItems.add(intIt) }
            } else if (it.trim().startsWith("Operation")) {
                monkeys.last().operationString = it.split(" ")
            } else if (it.trim().startsWith("Test")) {
                monkeys.last().testInt = it.split(" ").last().toInt()
                lcm *= monkeys.last().testInt
            } else if (it.trim().startsWith("If true")) {
                monkeys.last().throwToMonkeyIdTrue = it.split(" ").last().toInt()
            } else if (it.trim().startsWith("If false")) {
                monkeys.last().throwToMonkeyIdFalse = it.split(" ").last().toInt()
            } else {
                // do nothing
            }
        }
        for (i in 0 until rounds) {
            for (monkey in monkeys) {
                while (monkey.startingItems.isNotEmpty()) {
                    val action = monkey.inspectItem(lcm)
                    monkeys[action.second].startingItems.add(action.first)
                }
            }
            println(i)
        }
        monkeys.sortBy { it.inspectionCount }
        println("answer ${monkeys[monkeys.size - 1].inspectionCount * monkeys[monkeys.size - 2].inspectionCount}")
    }
}

class Monkey {
    val startingItems = ArrayDeque<Long>()
    var operationString = listOf<String>()
    var testInt = 0
    var throwToMonkeyIdTrue = 0
    var throwToMonkeyIdFalse = 0
    var inspectionCount = 0L

    /**
     * return Pair<item, monkeyIndex>
     */
    fun inspectItem(lcm: Long): Pair<Long, Int> {
        inspectionCount++
        var item = startingItems.removeFirst()
        item = performOperation(item)
        item %= lcm
        return if (item % testInt == 0L) {
            Pair(item, throwToMonkeyIdTrue)
        } else {
            Pair(item, throwToMonkeyIdFalse)
        }
    }

    private fun performOperation(item: Long): Long {
        val modifier = if (operationString.last() == "old") item else operationString.last().toLong()
        val operator = operationString[operationString.size - 2]
        return if (operator == "+") {
            item + modifier
        } else {
            item * modifier
        }
    }

    fun printInventory() {
        println(startingItems.toString())
    }
}