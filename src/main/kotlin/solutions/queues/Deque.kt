package solutions.queues

class Deque<T> : Iterable<T> {
    private lateinit var deque: Node<T>
    private var headNode: Node<T>? = null
    private var tailNode: Node<T>? = null
    private var size = 0

    private data class Node<T>(val value: T, var next: Node<T>?, var prev: Node<T>?)

    private fun initDeque(value: T) {
        deque = Node(value, null, null)
        headNode = deque
        tailNode = deque
        size = 1
    }

    fun size(): Int = size
    fun isEmpty(): Boolean = size == 0

    fun addFirst(newItem: T) {
        if (newItem == null) throw IllegalArgumentException("null items cannot be inserted")
        if (isEmpty()) initDeque(newItem)
        else {
            val oldHeadNode = headNode!!
            headNode = Node(newItem, oldHeadNode, null)
            oldHeadNode.prev = headNode
            size++
        }
    }

    fun addLast(newItem: T) {
        if (newItem == null) throw IllegalArgumentException("null items cannot be inserted")
        if (isEmpty()) initDeque(newItem)
        else {
            val newNode = Node(newItem, null, tailNode!!)
            tailNode!!.next = newNode
            tailNode = newNode
            size++
        }
    }

    fun removeFirst(): T {
        if (isEmpty()) throw NoSuchElementException("queue is empty")
        val oldHeadNode = headNode!!
        headNode = oldHeadNode.next
        headNode?.prev = null
        size--
        return oldHeadNode.value
    }

    fun removeLast(): T {
        if (isEmpty()) throw NoSuchElementException("queue is empty")
        val oldTailNode = tailNode!!
        tailNode = oldTailNode.prev
        tailNode?.next = null
        size--
        return oldTailNode.value
    }

    override fun iterator(): Iterator<T> = DequeIterator(size, headNode)

    private class DequeIterator<T>(private val size: Int, headNode: Node<T>?) : Iterator<T> {
        var counter = 1
        var pointerNode: Node<T>? = headNode
        override fun hasNext(): Boolean = counter <= size
        override fun next(): T {
            if (counter > size) throw NoSuchElementException("iterator already consumed")
            counter++
            val pointerVal = pointerNode!!.value
            pointerNode = pointerNode!!.next
            return pointerVal
        }

        fun remove(): Unit = throw UnsupportedOperationException("remove is not supported")
    }
}