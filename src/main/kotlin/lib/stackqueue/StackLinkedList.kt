package lib.stackqueue

class StackLinkedList<T> : Iterable<T> {
    private var first: Node<T>? = null
    var size = 0
        private set

    private data class Node<T>(private val newItem: T, private val nextNode: Node<T>?) {
        val item = newItem
        val next = nextNode
    }

    fun isEmpty(): Boolean = first == null

    fun push(newItem: T) {
        val oldFirst = first
        first = Node(newItem, oldFirst)
        size++
    }

    fun pop(): T {
        if (isEmpty()) throw NoSuchElementException("Stack is empty.")
        val oldItem = first!!.item
        first = first!!.next
        size--
        return oldItem
    }

    fun peek(): T {
        if (isEmpty()) throw NoSuchElementException("Stack is empty.")
        return first!!.item
    }

    override fun iterator(): Iterator<T> = StackIterator(first)

    private class StackIterator<T>(first: Node<T>?) : Iterator<T> {
        private var currentNode = first
        override fun hasNext(): Boolean = currentNode != null
        override fun next(): T {
            val item = currentNode!!.item
            currentNode = currentNode!!.next
            return item
        }
    }
}