import java.io.File

class Day02 {

    val oppenentChoiceMap: Map<String, RockPaperScissors> =
        mapOf("A" to RockPaperScissors.ROCK, "B" to RockPaperScissors.PAPER, "C" to RockPaperScissors.SCISSORS)
    val playerChoiceMap: Map<String, RockPaperScissors> =
        mapOf("X" to RockPaperScissors.ROCK, "Y" to RockPaperScissors.PAPER, "Z" to RockPaperScissors.SCISSORS)
    val gameOutcomeMap: Map<String, GameOutcome> = mapOf("X" to GameOutcome.LOSE, "Y" to GameOutcome.DRAW, "Z" to GameOutcome.WIN)

    var score = 0

    init {
        File(javaClass.getResource("inputs/day2input.txt").toURI()).readLines().map {
            val line = it.split(" ")
            val opponentChoice = line[0]
            val myChoice = line[1]
            //Part 1
//            val roundScore = playerChoiceMap[myChoice]?.getPoints(oppenentChoiceMap[opponentChoice] ?: RockPaperScissors.ROCK) ?: 0
//            score += roundScore

            //Part 2
            val gameOutcome = gameOutcomeMap[myChoice]
            score += when (gameOutcome) {
                GameOutcome.LOSE -> getSignToLose(oppenentChoiceMap[opponentChoice]!!).getPoints(oppenentChoiceMap[opponentChoice] ?: RockPaperScissors.ROCK)
                GameOutcome.DRAW -> getSignToDraw(oppenentChoiceMap[opponentChoice]!!).getPoints(oppenentChoiceMap[opponentChoice] ?: RockPaperScissors.ROCK)
                GameOutcome.WIN -> getSignToWin(oppenentChoiceMap[opponentChoice]!!).getPoints(oppenentChoiceMap[opponentChoice] ?: RockPaperScissors.ROCK)
                null -> 0
            }
        }
    }

    fun main(args: Array<String>) {
        println("score = $score")
    }

    fun getSignToLose(opponent: RockPaperScissors) : RockPaperScissors {
        return when (opponent) {
            RockPaperScissors.ROCK -> RockPaperScissors.SCISSORS
            RockPaperScissors.PAPER -> RockPaperScissors.ROCK
            RockPaperScissors.SCISSORS -> RockPaperScissors.PAPER
        }
    }

    fun getSignToDraw(opponent: RockPaperScissors) : RockPaperScissors {
        return opponent
    }

    fun getSignToWin(opponent: RockPaperScissors) : RockPaperScissors {
        return when (opponent) {
            RockPaperScissors.ROCK -> RockPaperScissors.PAPER
            RockPaperScissors.PAPER -> RockPaperScissors.SCISSORS
            RockPaperScissors.SCISSORS -> RockPaperScissors.ROCK
        }
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

    enum class GameOutcome(val points: Int){
        LOSE(0),
        DRAW(3),
        WIN(6)
    }
}