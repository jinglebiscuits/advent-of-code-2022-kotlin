import java.io.File

class Day08 {

    val rows = mutableListOf<MutableList<Int>>()

    init {
        File(javaClass.getResource("inputs/day8input.txt").toURI()).readLines().map {
            rows.add(mutableListOf())
            it.map { char -> rows.last().add(char.digitToInt()) }
        }
        part1()
    }

    private fun part1() {
        val hiddenTrees = Array(rows.size) { IntArray(rows[0].size) { 1 } }
        for (row in 0 until rows.size) {
            for (column in 0 until rows[0].size) {
                if (isSeenFromEdge(row, column)) {
                    hiddenTrees[row][column] = 0
                }
            }
        }
        val sum = hiddenTrees.sumOf {
            it.sum()
        }
        println(rows.size * rows[0].size - sum)
    }

    private fun isSeenFromEdge(rowPos: Int, columnPos: Int): Boolean {
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
            return seenFromTop || seenFromBottom || seenFromLeft || seenFromRight
        }
    }
}