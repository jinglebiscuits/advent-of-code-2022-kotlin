import Util.TopThreeList
import java.io.File

class Day01 {
    var tempTotal = 0
    val topThreeList = TopThreeList()

    init {
        File(javaClass.getResource("inputs/1a.txt").toURI()).readLines().map {
            if (it.isEmpty()) {
                topThreeList.add(tempTotal)
                tempTotal = 0
            } else {
                tempTotal += it.toInt()
            }
        }
    }

    fun main(args: Array<String>) {
        println(topThreeList.first())
        println(topThreeList.sum())
    }
}