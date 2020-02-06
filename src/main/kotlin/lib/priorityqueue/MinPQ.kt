package lib.priorityqueue

import lib.utils.ArrayUtils

@Suppress("UNCHECKED_CAST")
class MinPQ<Key : Comparable<Key>> : Iterable<Key> {
    private var pq: Array<Any?> = arrayOfNulls(2)
    var size = 0

    fun isEmpty() = size == 0

    fun insert(key: Key) {
        if (size == pq.size - 1) resize(2 * (size + 1))
        pq[++size] = key
        swim(size)
    }

    fun delMin(): Key {
        if (isEmpty()) throw NoSuchElementException("queue is empty")
        val minValue = pq[1] as Key
        ArrayUtils.exch(pq, 1, size--)
        pq[size + 1] = null
        sink(1)
        return minValue
    }

    private fun swim(k: Int) {
        var index = k
        while (index > 1) {
            val currentNodeValue = pq[index] as Key
            val parentNodeValue = pq[index / 2] as Key
            if (currentNodeValue < parentNodeValue) {
                ArrayUtils.exch(pq, index, index / 2)
                index /= 2
            } else break
        }
    }

    private fun sink(k: Int) {
        var index = k
        while (2 * index <= size) {
            val j = 2 * index
            val currentValue = pq[index] as Key
            val indexToSwitch = when (j) {
                size -> j
                else -> if (pq[j] as Key > pq[j + 1] as Key) j + 1 else j
            }
            if (currentValue > pq[indexToSwitch] as Key) {
                ArrayUtils.exch(pq, index, indexToSwitch)
                index = indexToSwitch
            } else break
        }
    }

    override fun iterator(): Iterator<Key> = MinPQIterator()
    private inner class MinPQIterator : Iterator<Key> {
        private var counter = 1
        override fun hasNext(): Boolean = counter != size + 1
        override fun next(): Key {
            if (counter > size) throw UnsupportedOperationException("iterator already consumed")
            return pq[counter++] as Key
        }
    }

    private fun resize(length: Int) {
        if (length <= size) return
        val temp: Array<Any?> = arrayOfNulls(length)
        for (i in 0..size) temp[i] = pq[i]
        pq = temp
    }
}