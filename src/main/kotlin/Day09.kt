import java.awt.Point
import java.io.File
import kotlin.math.atan
import kotlin.math.pow
import kotlin.math.sqrt

class Day09 {

    private val headLocation = mutableSetOf(Point(0, 0))
    private var currentHeadLocation = Point(0, 0)
    private val tailLocation = mutableSetOf(Point(0, 0))
    private var currentTailLocation = Point(0, 0)

    init {
        File(javaClass.getResource("inputs/day9input.txt").toURI()).readLines().map {
            println(it)
            processMove(it.split(" "))
        }
        println("answer: ${tailLocation.size}")
    }

    private fun processMove(line: List<String>) {
        when (line[0]) {
            "U" -> {
                for (i in 0 until line[1].toInt()) {
                    moveUp()
                }
            }
            "D" -> {
                for (i in 0 until line[1].toInt()) {
                    moveDown()
                }
            }
            "L" -> {
                for (i in 0 until line[1].toInt()) {
                    moveLeft()
                }
            }
            "R" -> {
                for (i in 0 until line[1].toInt()) {
                    moveRight()
                }
            }
        }
    }

    private fun moveUp() {
        currentHeadLocation = Point(currentHeadLocation.x, currentHeadLocation.y + 1)
        headLocation.add(currentHeadLocation)
        moveTail()
    }

    private fun moveDown() {
        currentHeadLocation = Point(currentHeadLocation.x, currentHeadLocation.y - 1)
        headLocation.add(currentHeadLocation)
        moveTail()
    }

    private fun moveLeft() {
        currentHeadLocation = Point(currentHeadLocation.x - 1, currentHeadLocation.y)
        headLocation.add(currentHeadLocation)
        moveTail()
    }

    private fun moveRight() {
        currentHeadLocation = Point(currentHeadLocation.x + 1, currentHeadLocation.y)
        headLocation.add(currentHeadLocation)
        moveTail()
    }

    private fun moveTail() {
        val diff = Point(currentHeadLocation.x - currentTailLocation.x, currentHeadLocation.y - currentTailLocation.y)
        if (sqrt(diff.x.toDouble().pow(2.toDouble()) + diff.y.toDouble().pow(2.toDouble())) > 1.5) {
            var direction = 90.0
            try {
                direction = atan((diff.y.toDouble() / diff.x.toDouble())) * 180 / Math.PI
            } catch (ignored: ArithmeticException) {
            }
            if (diff.x < 0) {
                direction += 180.0
            }
            if (direction == 90.0 && diff.y < 0) {
                direction = -90.0
            }
            if (direction == 0.0) {
                currentTailLocation = Point(currentTailLocation.x + 1, currentTailLocation.y)
            } else if (direction > 0.0 && direction < 90.0) {
                currentTailLocation = Point(currentTailLocation.x + 1, currentTailLocation.y + 1)
            } else if (direction == 90.0) {
                currentTailLocation = Point(currentTailLocation.x, currentTailLocation.y + 1)
            } else if (direction > 90.0 && direction < 180.0) {
                currentTailLocation = Point(currentTailLocation.x - 1, currentTailLocation.y + 1)
            } else if (direction == 180.0) {
                currentTailLocation = Point(currentTailLocation.x - 1, currentTailLocation.y)
            } else if (direction > 180.0 && direction < 270.0) {
                currentTailLocation = Point(currentTailLocation.x - 1, currentTailLocation.y - 1)
            } else if (direction == -90.0) {
                currentTailLocation = Point(currentTailLocation.x, currentTailLocation.y - 1)
            } else {
                currentTailLocation = Point(currentTailLocation.x + 1, currentTailLocation.y - 1)
            }
            tailLocation.add(currentTailLocation)
        }

    }
}