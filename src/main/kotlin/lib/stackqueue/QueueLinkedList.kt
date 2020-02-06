package lib.stackqueue

class QueueLinkedList<T> : Iterable<T> {
    private var first: Node<T>? = null
    private var last: Node<T>? = null
    var size = 0
        private set

    private data class Node<T>(private val newItem: T, private val nextNode: Node<T>?) {
        val item = newItem
        var next = nextNode
    }

    fun isEmpty(): Boolean = first == null && last == null

    fun push(newItem: T) {
        val oldLast = last
        last = Node(newItem, null)
        if (first == null) first = last
        else oldLast!!.next = last
        size++
    }

    fun pop(): T {
        if (isEmpty()) throw NoSuchElementException("Queue is empty.")
        val item = first!!.item
        first = first!!.next
        if (first == null) last = null
        size--
        return item
    }

    override fun iterator(): Iterator<T> = QueueIterator(first)

    private class QueueIterator<T>(first: Node<T>?) : Iterator<T> {
        private var currentNode = first
        override fun hasNext(): Boolean = currentNode != null
        override fun next(): T {
            val currentItem = currentNode!!.item
            currentNode = currentNode!!.next
            return currentItem
        }
    }
}
