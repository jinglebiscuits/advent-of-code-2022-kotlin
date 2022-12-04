import java.io.File

class Day03 {

    private val lowerCaseMin = 'a'.code
    private val lowerCaseMax = 'z'.code
    private val upperCaseMin = 'A'.code
    private var score = 0

    init {
        File(javaClass.getResource("inputs/day3input.txt").toURI()).readLines().map {
            val firstString = it.substring(0, it.length/2)
            val secondString = it.substring(it.length/2, it.length)
            var matchFound = false
            firstString.forEach {
                if (matchFound) return@forEach
                val match = secondString.firstOrNull { char ->
                    char == it
                }
                if (match == null) {
                    return@forEach
                } else {
                    matchFound = true
                    println(match)
                    score += charCodeToPriority(match.code)
                }
            }
            println("score = $score")
        }
    }

    fun main() {

    }

    private fun charCodeToPriority(charCode: Int): Int {
        return if (charCode in lowerCaseMin..lowerCaseMax) {
            charCode - lowerCaseMin + 1
        } else {
            charCode - upperCaseMin + 27
        }
    }
}