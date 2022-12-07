import java.io.File

class Day07 {

    private val directories = mutableMapOf<String, Directory>()
    var currentDirectory: Directory

    init {
        directories["/"] = Directory("/", null)
        currentDirectory = directories["/"]!!
        File(javaClass.getResource("inputs/day7input.txt").toURI()).readLines().map {
            if (it.startsWith("$")) {
                processCommand(it)
            } else {
                processOutput(it)
            }
        }
        part1()
        part2()
    }

    private fun part1() {
        println("answer " + directories.values.filter { it.getSize() <= 100000 }.sumOf { it.getSize() })
    }

    private fun part2() {
        val totalSpace = 70000000
        val spaceForUpdate = 30000000
        val usedSpace = directories["/"]?.getSize() ?: 0
        val neededSpace = usedSpace + spaceForUpdate - totalSpace
        val sorted = directories.values.sortedBy { it.getSize() }
        val first = sorted.first { it.getSize() >= neededSpace }
        println(first.getSize())
    }

    private fun processCommand(line: String) {
        val list = line.split(" ")
        if (list[1] == "cd") {
            if (list[2] == "..") {
                currentDirectory.parentDirectory?.let { currentDirectory = it }
            } else {
                if (!directories.contains(currentDirectory.getPath() + list[2] + "/")) {
                    val newDir = Directory(list[2], currentDirectory)
                    directories[newDir.getPath()] = newDir
                }
                currentDirectory.findDirectory(list[2])?.let { currentDirectory = it }
            }
        } else if (list[1] == "ls") {
            println("list")
        }
    }

    private fun processOutput(line: String) {
        val list = line.split(" ")
        if (list[0] == "dir") {
            val newDir = Directory(list[1], currentDirectory)
            if (!directories.contains(newDir.getPath())) {
                directories[newDir.getPath()] = newDir
            }
            currentDirectory.addDirectory(newDir)
        } else {
            currentDirectory.addFile(list[1], list[0].toInt())
        }
    }

    class Directory(val name: String, val parentDirectory: Directory?) {
        private val files: MutableMap<String, Int> = mutableMapOf()
        private val directories: MutableMap<String, Directory> = mutableMapOf()

        fun addDirectory(dir: Directory) {
            directories[dir.name] = dir
        }

        fun findDirectory(name: String): Directory? {
            return directories[name]
        }

        fun addFile(name: String, size: Int) {
            files[name] = size
        }

        fun getSize(): Int {
            println(name)
            var sum = 0
            directories.values.forEach {
                sum += it.getSize()
            }
            return sum + files.values.sum()
        }

        fun getPath(): String {
            return if (parentDirectory == null) {
                name
            } else {
                parentDirectory.getPath() + "$name/"
            }
        }
    }
}