import java.io.File

class Day08 {

    val rows = mutableListOf<MutableList<Int>>()

    init {
        File(javaClass.getResource("inputs/day8input_test.txt").toURI()).readLines().map {
            rows.add(mutableListOf())
            it.map { char -> rows.last().add(char.digitToInt()) }
        }
        part2()
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

    private fun part2() {
        val scenicScores = mutableListOf<Int>()
        for (row in 0 until rows.size) {
            for (column in 0 until rows[0].size) {
                scenicScores.add(getScenicScoreFor(row, column))
            }
        }
        println("answer ${scenicScores.max()}")
    }

    private fun isSeenFromEdge(rowPos: Int, columnPos: Int): Boolean {
        if (rowPos == 0 || rowPos == rows.size - 1 || columnPos == 0 || columnPos == rows[0].size - 1) {
            return true
        } else {
            val treeHeight = rows[rowPos][columnPos]
            val column = getColumnFor(columnPos)
            val seenFromTop = !column.subList(0, rowPos).any { it >= treeHeight }
            val seenFromBottom = !column.subList(rowPos + 1, rows.size).any { it >= treeHeight }
            val seenFromLeft = !rows[rowPos].subList(0, columnPos).any { it >= treeHeight }
            val seenFromRight = !rows[rowPos].subList(columnPos + 1, rows[rowPos].size).any { it >= treeHeight }
            return seenFromTop || seenFromBottom || seenFromLeft || seenFromRight
        }
    }

    private fun getScenicScoreFor(rowPos: Int, columnPos: Int): Int {
        val leftTrees = rows[rowPos].subList(0, columnPos).reversed()
        val rightTrees = rows[rowPos].subList(columnPos, rows[rowPos].size)
        val column = getColumnFor(columnPos)
        val topTrees = column.subList(0, rowPos).reversed()
        val botTrees = column.subList(rowPos, column.size)
        val leftScore = leftTrees
            .map { if (it <= rows[rowPos][columnPos]) 1 else 0 }
            .also { println(it) }
            .takeWhile { it == 1 }
            .also { println(it) }
            .sum()
        println("$rowPos $columnPos $leftScore")
        return leftScore
    }

    private fun getColumnFor(column: Int): MutableList<Int> {
        return rows
            .flatten()
            .filterIndexed { index, i -> (index - column) % (rows[0].size) == 0 }.toMutableList()
    }
}