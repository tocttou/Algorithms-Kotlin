package lib.stackqueue

open class QueueResizingArray<T> : Iterable<T> {
    protected var head = 0
    protected var tail = 0
    protected var queue: Array<in Any?> = Array(1, { null })
    val size: Int
        get() = tail - head

    fun isEmpty(): Boolean = tail - head == 0

    open fun push(newItem: T) {
        if (tail == queue.size) resize(2 * (tail - head))
        queue[tail++] = newItem
    }

    open fun pop(): T {
        if (isEmpty()) throw NoSuchElementException("Queue is empty.")
        val oldItem = queue[head]
        queue[head++] = null
        if (tail - head == queue.size / 4) {
            if (queue.size == 1) resize(2)
            else resize(queue.size / 2)
        }
        @Suppress("UNCHECKED_CAST") return oldItem as T
    }

    protected fun resize(length: Int) {
        val copyQueue: Array<in Any?> = Array(length, { null })
        for ((index, i) in (head..(tail - 1)).withIndex()) copyQueue[index] = queue[i]
        queue = copyQueue
        tail -= head
        head = 0
    }

    override fun iterator(): Iterator<T> = QueueIterator(head, tail, queue)

    private class QueueIterator<T>(
        head: Int, private val tail: Int, private val queue: Array<Any?>
    ) : Iterator<T> {
        private var currentIndex = head
        override fun hasNext(): Boolean = currentIndex != tail
        @Suppress("UNCHECKED_CAST")
        override fun next(): T = queue[currentIndex++] as T
    }
}