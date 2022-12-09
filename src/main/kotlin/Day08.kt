import java.io.File

class Day08 {

    val rows = mutableListOf<MutableList<Int>>()

    init {
        File(javaClass.getResource("inputs/day8input.txt").toURI()).readLines().map {
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
        val rightTrees = if (columnPos == rows[rowPos].size - 1) emptyList() else (rows[rowPos].subList(columnPos + 1, rows[rowPos].size))
        val column = getColumnFor(columnPos)
        val topTrees = column.subList(0, rowPos).reversed()
        val botTrees = if (rowPos == rows.size -1) emptyList() else (column.subList(rowPos + 1, column.size))
        val leftScore = getDirectionalScore(rows[rowPos][columnPos], leftTrees)
        val rightScore = getDirectionalScore(rows[rowPos][columnPos], rightTrees)
        val topScore = getDirectionalScore(rows[rowPos][columnPos], topTrees)
        val botScore = getDirectionalScore(rows[rowPos][columnPos], botTrees)
        return leftScore*rightScore*topScore*botScore
    }

    private fun getDirectionalScore(treeHeight: Int, trees: List<Int>) : Int {
        var leftScore = if (trees.isEmpty()) -2 else trees
            .indexOfFirst { it >= treeHeight } + 1
        when (leftScore) {
            -2 -> leftScore = 0
            0 -> leftScore = trees.size
        }
        return leftScore
    }

    private fun getColumnFor(column: Int): MutableList<Int> {
        return rows
            .flatten()
            .filterIndexed { index, i -> (index - column) % (rows[0].size) == 0 }.toMutableList()
    }
}