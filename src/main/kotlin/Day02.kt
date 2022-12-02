import java.io.File

class Day02 {

    val oppenentMap: Map<String, RockPaperScissors> =
        mapOf("A" to RockPaperScissors.ROCK, "B" to RockPaperScissors.PAPER, "C" to RockPaperScissors.SCISSORS)
    val playerMap: Map<String, RockPaperScissors> =
        mapOf("X" to RockPaperScissors.ROCK, "Y" to RockPaperScissors.PAPER, "Z" to RockPaperScissors.SCISSORS)

    var score = 0

    init {
        File(javaClass.getResource("inputs/day2input.txt").toURI()).readLines().map {
            val line = it.split(" ")
            val opponentChoice = line[0]
            val myChoice = line[1]
            val roundScore = playerMap[myChoice]?.getPoints(oppenentMap[opponentChoice] ?: RockPaperScissors.ROCK) ?: 0
            score += roundScore
        }
    }

    fun main(args: Array<String>) {
        println("score = $score")
    }

    enum class RockPaperScissors(val points: Int) {
        ROCK(1),
        PAPER(2),
        SCISSORS(3);

        fun getPoints(other: RockPaperScissors): Int {
            when (other) {
                ROCK -> {
                    return when (this) {
                        ROCK -> points + 3
                        PAPER -> points + 6
                        SCISSORS -> points + 0
                    }
                }
                PAPER -> {
                    return when (this) {
                        ROCK -> points + 0
                        PAPER -> points + 3
                        SCISSORS -> points + 6
                    }
                }
                SCISSORS -> {
                    return when (this) {
                        ROCK -> points + 6
                        PAPER -> points + 0
                        SCISSORS -> points + 3
                    }
                }
            }
        }
    }
}