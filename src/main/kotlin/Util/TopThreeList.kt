package Util

class TopThreeList : AbstractMutableList<Int>() {
    val backingArray = ArrayDeque(listOf(0, 0, 0))

    override fun add(index: Int, element: Int) {
        TODO("Not yet implemented")
    }

    override fun add(element: Int): Boolean {
        if (backingArray[0] < element) {
            backingArray.addFirst(element)
            backingArray.removeLast()
        } else if (backingArray[1] < element) {
            backingArray.add(1, element)
            backingArray.removeLast()
        } else if (backingArray[2] < element) {
            backingArray.add(2, element)
            backingArray.removeLast()
        }
        return true
    }

    override fun removeAt(index: Int): Int {
        TODO("Not yet implemented")
    }

    override fun set(index: Int, element: Int): Int {
        TODO("Not yet implemented")
    }

    override val size: Int
        get() = 3

    override fun get(index: Int): Int {
        return backingArray[index.coerceAtMost(2).coerceAtLeast(0)]
    }
}