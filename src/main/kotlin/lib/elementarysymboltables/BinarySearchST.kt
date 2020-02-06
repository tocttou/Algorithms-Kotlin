package lib.elementarysymboltables

import lib.stackqueue.QueueResizingArray

@Suppress("UNCHECKED_CAST")
class BinarySearchST<Key : Comparable<Key>, Value> : QueueResizingArray<Pair<Key, Value>>() {
    fun max(): Key? {
        if (size == 0) return null
        return (queue[tail - 1] as Pair<Key, Value>).first
    }

    fun min(): Key? {
        if (size == 0) return null
        return (queue[0] as Pair<Key, Value>).first
    }

    fun put(key: Key, value: Value) {
        if (value == null) {
            delete(key)
            return
        }
        val currIndex = findIndex(key)
        if (currIndex == -1) {
            val currMaxIndex = greatestIndexLessThan(key)
            if (tail == queue.size) resize(2 * (tail - head))
            insertAt(currMaxIndex + 1, Pair(key, value))
        } else queue[currIndex] = Pair(key, value)
    }

    private fun greatestIndexLessThan(key: Key): Int = findIndex(key, "lo") - 1

    private fun smallestIndexGreaterThan(key: Key): Int = findIndex(key, "hi") + 1

    fun get(key: Key): Value? {
        val index = findIndex(key)
        return if (index == -1) null else (queue[index] as Pair<Key, Value>).second
    }

    fun contains(key: Key): Boolean = findIndex(key) != -1

    fun floor(key: Key): Key? {
        if (size == 0) return null
        if (contains(key)) return key
        val greatestIndexLessThanKey = greatestIndexLessThan(key)
        if (greatestIndexLessThanKey == -1) return null
        return (queue[greatestIndexLessThanKey] as Pair<Key, Value>).first
    }

    fun ceiling(key: Key): Key? {
        if (size == 0) return null
        if (contains(key)) return key
        val smallestIndexGreaterThanKey = smallestIndexGreaterThan(key)
        if (smallestIndexGreaterThanKey == size) return null
        return (queue[smallestIndexGreaterThanKey] as Pair<Key, Value>).first
    }

    fun rank(key: Key): Int = greatestIndexLessThan(key) + 1

    fun select(rank: Int): Key? {
        if (size == 0) return null
        if (rank > size || rank < 0) throw IllegalArgumentException(
            "rank must be greater than 0 and less than size of ST"
        )
        return (queue[rank] as Pair<Key, Value>).first
    }

    fun deleteMin() = delete(min())

    fun deleteMax() = delete(max())

    fun size() = size

    fun size(lo: Key, hi: Key): Int {
        if (hi < lo) throw IllegalArgumentException("hi key must be greater than lo key")
        if (min() == null || hi < min()!!) return 0
        if (max() == null || lo > max()!!) return 0
        return (if (hi > floor(hi)!!) rank(hi) else (rank(hi) + 1)) - rank(lo)
    }

    fun keys(): Iterable<Key> {
        return when (size) {
            0 -> listOf()
            else -> List(size) { (queue[it] as Pair<Key, Value>).first }
        }
    }

    fun keys(lo: Key, hi: Key): Iterable<Key> {
        if (hi < lo) throw IllegalArgumentException("hi key must be greater than lo key")
        return keys().filter { it in lo..hi }
    }

    override fun pop(): Pair<Key, Value> = throw UnsupportedOperationException("pop is disabled")

    override fun push(newItem: Pair<Key, Value>) =
        throw UnsupportedOperationException("push is disabled")

    private fun maxIndex(): Int {
        if (size == 0) return -1
        var max = if (queue[0] == null) null else (queue[0] as Pair<Key, Value>).first
        var maxInd = -1
        val comparator = KeyComparator<Key>()
        for ((index, element) in queue.withIndex()) {
            val currKey = if (element == null) null else (element as Pair<Key, Value>).first
            if (comparator.compare(currKey, max) >= 0) {
                if (currKey != null) {
                    maxInd = index
                    max = currKey
                }
            }
        }
        return maxInd
    }

    private fun minIndex(): Int {
        if (size == 0) return -1
        var min = if (queue[0] == null) null else (queue[0] as Pair<Key, Value>).first
        var minInd = -1
        val comparator = KeyComparator<Key>()
        for ((index, element) in queue.withIndex()) {
            val currKey = if (element == null) null else (element as Pair<Key, Value>).first
            if (comparator.compare(currKey, min) <= 0) {
                if (currKey != null) {
                    min = currKey
                    minInd = index
                }
            }
        }
        return minInd
    }

    fun delete(key: Key?) {
        if (key == null) throw IllegalArgumentException("key cannot be null")
        val index = findIndex(key)
        if (index == -1) throw NoSuchElementException("key-value pair not found")
        queue[index] = null
        rearrange()
        if (tail - head == queue.size / 4) {
            if (queue.size == 1) resize(2)
            else resize(queue.size / 2)
        }
    }

    private class KeyComparator<Key : Comparable<Key>> : Comparator<Any?> {
        override fun compare(o1: Any?, o2: Any?): Int {
            if (o1 == o2) return 0
            if (o1 == null && o2 != null) return 1
            if (o1 != null && o2 == null) return -1
            val o1f = o1 as Key
            val o2f = o2 as Key
            return when {
                o1f < o2f -> -1
                o1f > o2f -> 1
                else -> 0
            }
        }
    }

    private fun findIndex(key: Key, type: String = "index"): Int {
        var lo = 0
        var hi = queue.size - 1
        val comparator = KeyComparator<Key>()
        while (lo <= hi) {
            val mid = lo + (hi - lo) / 2
            val currentValue =
                if (queue[mid] == null) null else (queue[mid] as Pair<Key, Value>).first
            when {
                comparator.compare(currentValue, key) < 0 -> lo = mid + 1
                comparator.compare(currentValue, key) > 0 -> hi = mid - 1
                else -> return mid
            }
        }
        return when (type) {
            "lo" -> lo
            "hi" -> hi
            else -> -1
        }
    }

    private fun rearrange() {
        val arr = arrayOfNulls<Any?>(queue.size)
        var counter = 0
        for (element in queue) {
            if (element != null) arr[counter++] = element
        }
        queue = arr
        head = 0
        tail = counter
    }

    private fun insertAt(index: Int, pair: Pair<Key, Value>) {
        val arr = arrayOfNulls<Any?>(queue.size)
        var counter = 0
        for (i in 0..(index - 1)) arr[i] = queue[counter++]
        arr[counter] = pair
        for (i in (index + 1)..(size)) arr[i] = queue[counter++]
        queue = arr
        head = 0
        tail = counter + 1
    }
}