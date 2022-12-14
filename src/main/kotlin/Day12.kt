import java.io.File

class Day12 {

    val nodes = mutableSetOf<Node>()
    val locations = mutableListOf<Location>()
    var goal: Location = Location(0, 0, 0)
    var goalNode: Node? = null
    var gridSize = Pair(0, 0)
    var nodesToProcess = mutableListOf<Node>()

    init {
        var y = 0
        var x = 0
        File(javaClass.getResource("inputs/day12input_test.txt").toURI()).readLines().map {
            println(it)
            x = 0
            for (char in it) {
                if (char == 'S') {
                    locations.add(Location(x, y, 'a'.code))
                } else if (char == 'E') {
                    locations.add(Location(x, y, 'z'.code))
                    goal = Location(x, y, 'z'.code)
                } else {
                    locations.add(Location(x, y, char.code))
                }
                x++
            }
            y++
        }
        gridSize = Pair(x, y) // x across and y down
        part1()
    }

    private fun part1() {
        val start = Node(locations[0], null)
        nodes.add(start)
        nodesToProcess.add(start)
        while (nodesToProcess.isNotEmpty()) {
            val list = nodesToProcess.removeFirst().getChildren(locations, gridSize)
            val prunedList = list.filter { !nodesToProcess.contains(it) }
            nodesToProcess.addAll(prunedList)
            nodes.addAll(list)
            for (node in list) {
                println("${node.location.posX}, ${node.location.posY}")
                if (node.location.posX == goal.posX && node.location.posY == goal.posY) {
                    nodesToProcess.clear()
                    goalNode = node
                }
            }
        }
        println("end")
        println(nodes)
        var steps = 0
        goalNode?.let {
            var parent = it.parentNode
            while (parent != null) {
                steps++
                parent = parent.parentNode
            }
        }
        println("answer $steps")
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
            location != parentNode.location && (location.height <= this.location.height + 1)
        } else {
            location.height <= this.location.height + 1
        }
    }

    private fun indexUp(gridSize: Pair<Int, Int>): Int {
        if (location.posY == 0) {
            throw IndexOutOfBoundsException("no no")
        } else {
            return indexFromPosition(location.posX, location.posY - gridSize.first, gridSize)
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

data class Location(val posX: Int, val posY: Int, val height: Int)