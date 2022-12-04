import java.io.File

class Day03 {

    private val lowerCaseMin = 'a'.code
    private val lowerCaseMax = 'z'.code
    private val upperCaseMin = 'A'.code
    private var score = 0

    private var groupPacks = mutableListOf("", "", "")
    private var groupCount = 0
    private var matchFound = false

    init {
        File(javaClass.getResource("inputs/day3input.txt").toURI()).readLines().map {
            part2(it)
        }
    }

    fun main() {

    }

    private fun part1(line: String) {
        val firstString = line.substring(0, line.length/2)
        val secondString = line.substring(line.length/2, line.length)
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

    private fun part2(line: String) {
        if (groupCount < 2){
            groupPacks[groupCount] = line
            groupCount ++
        } else {
            groupPacks[groupCount] = line
            groupPacks.sortBy { it.length }
            groupPacks[0].forEach {
                if (matchFound) return@forEach
                if (it in groupPacks[1] && it in groupPacks[2]) {
                    matchFound = true
                    println(it)
                    score += charCodeToPriority(it.code)
                }
            }
            matchFound = false
            groupCount = 0
        }
        println(score)
    }

    private fun charCodeToPriority(charCode: Int): Int {
        return if (charCode in lowerCaseMin..lowerCaseMax) {
            charCode - lowerCaseMin + 1
        } else {
            charCode - upperCaseMin + 27
        }
    }
}