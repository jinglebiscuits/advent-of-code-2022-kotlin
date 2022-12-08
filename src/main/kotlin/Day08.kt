import java.io.File
import java.util.*

class Day08 {

    val rows = mutableListOf<MutableList<Int>>()

    init {
        File(javaClass.getResource("inputs/day8input_test.txt").toURI()).readLines().map {
            rows.add(mutableListOf())
            it.map { char -> rows.last().add(char.digitToInt()) }
            println(it)
        }
        println(rows.flatten())
        println(rows.flatten().filterIndexed { index, i -> (index - 1) % (rows[0].size) == 0 })
        part1()
    }

    private fun part1() {
        val hiddenTrees = Collections.nCopies(rows.size, (Collections.nCopies(rows[0].size, 1)).toMutableList())
        for (row in 0 until rows.size) {
            for (column in 0 until rows[0].size) {
                if (isSeenFromEdge(row, column)) {
                    hiddenTrees[row][column] = 0
                }
            }
        }
        println(hiddenTrees.sumOf {
            it.sum()
        })
    }

    private fun isSeenFromEdge(rowPos: Int, columnPos: Int) : Boolean {
        if (rowPos == 0 || rowPos == rows.size - 1 || columnPos == 0 || columnPos == rows[0].size - 1) {
            return true
        } else {
            val treeHeight = rows[rowPos][columnPos]
            val column = rows
                .flatten()
                .filterIndexed { index, i -> (index - columnPos) % (rows[0].size) == 0 }
            val seenFromTop = !column.subList(0, rowPos).any { it >= treeHeight }
            val seenFromBottom = !column.subList(rowPos + 1, rows.size).any { it >= treeHeight }
            val seenFromLeft = !rows[rowPos].subList(0, columnPos).any { it >= treeHeight }
            val seenFromRight = !rows[rowPos].subList(columnPos + 1, rows[rowPos].size).any { it >= treeHeight }
            println("row $rowPos column $columnPos")
            return seenFromTop || seenFromBottom || seenFromLeft || seenFromRight
        }
    }
}