import java.io.File
import java.util.*
import kotlin.math.atan
import kotlin.math.pow
import kotlin.math.sqrt

class Day09 {

    private val tailLocation = mutableSetOf(Point(0, 0))
    private val knots = List(10) { Point(0, 0) }
    val file = File("part2.txt")

    init {
        file.writeText("")
        file.createNewFile()
        file.path
        File(javaClass.getResource("inputs/day9input.txt").toURI()).readLines().map {
            println(it)
            processMove(it.split(" "))
            file.appendText(it + "\n" + tailLocation.size.toString() + "\n" + knots.last() + "\n\n")
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
        knots[0].y += 1
        visualize()
        moveKnots()
    }

    private fun moveDown() {
        knots[0].y -= 1
        visualize()
        moveKnots()
    }

    private fun moveLeft() {
        knots[0].x -= 1
        visualize()
        moveKnots()
    }

    private fun moveRight() {
        knots[0].x += 1
        visualize()
        moveKnots()
    }

    private fun moveKnots() {
        for (i in 1 until knots.size) {
            val result = moveKnot(knots[i], knots[i-1])
            knots[i].x = result.x
            knots[i].y = result.y
            visualize()
        }
        if (!tailLocation.contains(knots.last())) {
            tailLocation.add(Point(knots.last().x, knots.last().y))
            file.appendText("adding ${knots.last()} to list\n")
        }
    }

    private fun moveKnot(follower: Point, leader: Point): Point {
        val diff = Point(leader.x - follower.x, leader.y - follower.y)
        var result = Point(follower.x, follower.y)
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
                result = Point(follower.x + 1, follower.y)
            } else if (direction > 0.0 && direction < 90.0) {
                result = Point(follower.x + 1, follower.y + 1)
            } else if (direction == 90.0) {
                result = Point(follower.x, follower.y + 1)
            } else if (direction > 90.0 && direction < 180.0) {
                result = Point(follower.x - 1, follower.y + 1)
            } else if (direction == 180.0) {
                result = Point(follower.x - 1, follower.y)
            } else if (direction > 180.0 && direction < 270.0) {
                result = Point(follower.x - 1, follower.y - 1)
            } else if (direction == -90.0) {
                result = Point(follower.x, follower.y - 1)
            } else {
                result = Point(follower.x + 1, follower.y - 1)
            }
        }
        return result
    }

    private fun visualize() {
        return
        val grid = mutableListOf<MutableList<String>>()
        for (i in 0 until 50) {
            val row = mutableListOf<String>()
            for (j in 0 until 50) {
                row.add(".")
            }
            grid.add(row)
        }
        for (i in knots.indices) {
            grid[knots[i].y + 25][knots[i].x + 25] = i.toString()
        }
        for (point in tailLocation) {
            grid[point.y + 25][point.x + 25] = "#"
        }
        grid[25][25] = "s"
        for (row in grid.reversed()) {
            println(row)
        }
        println("\n\n")
        Thread.sleep(25)
    }
}

class Point(var x: Int, var y: Int) {
    override fun equals(other: Any?): Boolean {
        return if (other is Point) {
            x == other.x && y == other.y
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        return Objects.hash(x, y)
    }

    override fun toString(): String {
        return "java.awt.Point[x=$x,y=$y]"
    }
}