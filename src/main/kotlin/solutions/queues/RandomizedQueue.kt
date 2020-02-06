package solutions.queues

import edu.princeton.cs.algs4.StdRandom
import lib.elementarysorts.applications.KnuthShuffle

class RandomizedQueue<T> : Iterable<T> {
    private var tail = 0
    private var size = 0
    private lateinit var queue: Array<Any?>
    private fun initQueue() {
        queue = arrayOfNulls(1)
        size = 1
        tail = 0
    }

    fun size(): Int = size
    fun isEmpty(): Boolean = size() == 0

    fun enqueue(newItem: T) {
        if (newItem == null) throw IllegalArgumentException("null items cannot be inserted")
        val wasEmpty = isEmpty()
        if (isEmpty()) initQueue()
        if (tail == queue.size) resize(2 * tail)
        queue[tail++] = newItem
        if (!wasEmpty) size++
    }

    fun dequeue(): T {
        if (isEmpty()) throw NoSuchElementException("queue is empty")
        if (size == queue.size / 4) {
            if (queue.size == 1) resize(2)
            else resize(queue.size / 2)
        }
        var randomIndex = StdRandom.uniform(queue.size)
        while (queue[randomIndex] == null) randomIndex = StdRandom.uniform(queue.size)
        val oldItem = queue[randomIndex]
        queue[randomIndex] = null
        size--
        @Suppress("UNCHECKED_CAST") return oldItem as T
    }

    fun sample(): T {
        if (isEmpty()) throw NoSuchElementException("queue is empty")
        resize(size)
        var randomIndex = StdRandom.uniform(tail)
        while (queue[randomIndex] == null) randomIndex = StdRandom.uniform(tail)
        val randomItem = queue[randomIndex]
        @Suppress("UNCHECKED_CAST") return randomItem as T
    }

    override fun iterator(): Iterator<T> = IterOnQueue(size(), queue)

    private inner class IterOnQueue<T>(size: Int, private val queue: Array<Any?>) : Iterator<T> {
        var counter = size - 1
        var indices: Array<Int> = arrayOf()

        init {
            if (counter >= 0) {
                indices = Array(size) { it }
                KnuthShuffle.shuffle(indices)
                this@RandomizedQueue.resize(size)
            }
        }

        override fun hasNext(): Boolean = counter >= 0
        override fun next(): T {
            if (counter < 0) throw NoSuchElementException("iterator already consumed")
            @Suppress("UNCHECKED_CAST") return queue[indices[counter--]] as T
        }

        fun remove() {
            throw UnsupportedOperationException("remove not supported")
        }
    }

    private fun resize(length: Int) {
        if (length <= 0) throw IllegalArgumentException("length must be greater than 0")
        if (length == queue.size) return
        val copyQueue = arrayOfNulls<Any?>(length)
        var counter = 0
        for (i in queue) if (i != null) copyQueue[counter++] = i
        queue = copyQueue
        tail = size
    }
}