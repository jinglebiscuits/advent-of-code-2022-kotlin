import java.io.File

class Day12 {

    val nodes = mutableSetOf<Node>()
    val locations = mutableListOf<Location>()
    val visitedLocations = mutableSetOf<Location>()
    var start: Location = Location(0, 0, 'a')
    var startLocations = mutableListOf<Location>()
    var goal: Location = Location(0, 0, 'a')
    var goalNode: Node? = null
    var gridSize = Pair(0, 0)
    var nodesToProcess = mutableListOf<Node>()

    init {
        var y = 0
        var x = 0
        File(javaClass.getResource("inputs/day12input.txt").toURI()).readLines().map {
            x = 0
            for (char in it) {
                if (char == 'S') {
                    locations.add(Location(x, y, 'a'))
                    start = Location(x, y, 'a')
                    startLocations.add(Location(x, y, 'a'))
                } else if (char == 'E') {
                    locations.add(Location(x, y, 'z'))
                    goal = Location(x, y, 'z')
                } else if (char == 'a') {
                    locations.add(Location(x, y, char))
                    startLocations.add(Location(x, y, char))
                } else {
                    locations.add(Location(x, y, char))
                }
                x++
            }
            y++
        }
        gridSize = Pair(x, y) // x across and y down
        part1()
    }

    private fun part1() {
        val answers = mutableListOf<Int>()
        for (startLocation in startLocations) {
            nodes.clear()
            nodesToProcess.clear()
            visitedLocations.clear()
            val start = Node(startLocation, null)
            nodes.add(start)
            nodesToProcess.add(start)
            while (nodesToProcess.isNotEmpty()) {
                val tempNode = nodesToProcess.removeFirst()
                val list = tempNode.getChildren(locations, gridSize)
                val prunedList = list.filter { !visitedLocations.contains(it.location) }
                nodesToProcess.addAll(prunedList)
                visitedLocations.addAll(list.map { it.location })
                nodes.addAll(list)
                for (node in prunedList) {
                    if (node.location.posX == goal.posX && node.location.posY == goal.posY) {
                        nodesToProcess.clear()
                        goalNode = node
                    }
                }
            }
            println("end")
            var steps = 0
            goalNode?.let {
                var parent = it.parentNode
                while (parent != null) {
                    steps++
                    parent = parent.parentNode
                }
            }
            println("answer $steps")
            answers.add(steps)
            visualize()
        }
        answers.sort()
        println(answers[0])
    }

    private fun visualize() {
        var viz = ""
        val list = mutableListOf<Location>()
        var parent = goalNode!!.parentNode
        while (parent != null) {
            list.add(parent.location)
            parent = parent.parentNode
        }
        for (i in 0 until locations.size) {
            if (i % gridSize.first == 0) {
                viz += "\n"
            }
            if (list.contains(locations[i])) {
                viz += "#"
            } else {
                viz += locations[i].height
            }
        }
        println(viz)
    }
}

class Node(val location: Location, val parentNode: Node?) {

    fun getChildren(locations: List<Location>, gridSize: Pair<Int, Int>): List<Node> {
        val children = mutableListOf<Node>()
        var location: Location
        try {
            location = locations[indexUp(gridSize)]
            if (isChildLocationAccessible(location)) {
                children.add(Node(location, this))
            }
        } catch (ignored: IndexOutOfBoundsException) {
        }
        try {
            location = locations[indexRight(gridSize)]
            if (isChildLocationAccessible(location)) {
                children.add(Node(location, this))
            }
        } catch (ignored: IndexOutOfBoundsException) {
        }
        try {
            location = locations[indexDown(gridSize)]
            if (isChildLocationAccessible(location)) {
                children.add(Node(location, this))
            }
        } catch (ignored: IndexOutOfBoundsException) {
        }
        try {
            location = locations[indexLeft(gridSize)]
            if (isChildLocationAccessible(location)) {
                children.add(Node(location, this))
            }
        } catch (ignored: IndexOutOfBoundsException) {
        }
        return children
    }

    private fun isChildLocationAccessible(location: Location): Boolean {
        return if (parentNode != null) {
            location != parentNode.location && (location.height.code <= this.location.height.code + 1)
        } else {
            location.height.code <= this.location.height.code + 1
        }
    }

    private fun indexUp(gridSize: Pair<Int, Int>): Int {
        if (location.posY == 0) {
            throw IndexOutOfBoundsException("no no")
        } else {
            return indexFromPosition(location.posX, location.posY - 1, gridSize)
        }
    }

    private fun indexRight(gridSize: Pair<Int, Int>): Int {
        if (location.posX >= gridSize.first - 1) {
            throw IndexOutOfBoundsException("no no")
        } else {
            return indexFromPosition(location.posX + 1, location.posY, gridSize)
        }
    }

    private fun indexDown(gridSize: Pair<Int, Int>): Int {
        if (location.posY >= gridSize.second - 1) {
            throw IndexOutOfBoundsException("no no")
        } else {
            return indexFromPosition(location.posX, location.posY + 1, gridSize)
        }
    }

    private fun indexLeft(gridSize: Pair<Int, Int>): Int {
        if (location.posX == 0) {
            throw IndexOutOfBoundsException("no no")
        } else {
            return indexFromPosition(location.posX - 1, location.posY, gridSize)
        }
    }

    private fun indexFromPosition(posX: Int, posY: Int, gridSize: Pair<Int, Int>): Int {
        return posY * gridSize.first + posX
    }
}

data class Location(val posX: Int, val posY: Int, val height: Char)