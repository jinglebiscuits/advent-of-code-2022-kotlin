import java.io.File

class Day01 {
    var tempTotal = 0
    var answer = 0

    init {
        File(javaClass.getResource("inputs/1a.txt").toURI()).readLines().map {
            if (it.isEmpty()) {
                if (tempTotal > answer) {
                    answer = tempTotal
                }
                tempTotal = 0
            } else {
                tempTotal += it.toInt()
            }
        }
    }

    fun main(args: Array<String>) {
        println(answer)
    }
}