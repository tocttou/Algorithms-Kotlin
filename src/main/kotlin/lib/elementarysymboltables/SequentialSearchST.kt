package lib.elementarysymboltables

import lib.quicksort.Quick

class SequentialSearchST<Key : Comparable<Key>, Value> {
    private var headNode: Node<Key, Value>? = null
    private var size = 0

    private data class Node<K, V>(
        var pair: Pair<K, V>, var next: Node<K, V>?, var prev: Node<K, V>?
    )

    private fun findNode(key: Key): Node<Key, Value>? {
        var node = headNode
        while (node != null) {
            if (node.pair.first == key) break
            else node = node.next
        }
        return node
    }

    fun put(key: Key, value: Value) {
        if (value == null) {
            delete(key)
            return
        }
        val pair = Pair(key, value)
        val existingNode = findNode(pair.first)
        if (existingNode != null) existingNode.pair = pair
        else {
            val newNode = Node(pair, headNode, null)
            headNode?.prev = newNode
            headNode = newNode
            size++
        }
    }

    fun get(key: Key): Value? = findNode(key)?.pair?.second

    fun delete(key: Key?) {
        if (key == null) throw IllegalArgumentException("key cannot be null")
        val node = findNode(key)
        if (node == null) throw NoSuchElementException("key-value pair not found")
        else {
            node.prev?.next = node.next
            node.next?.prev = node.prev
            if (node == headNode) headNode = node.next
            size--
        }
    }

    fun contains(key: Key): Boolean = findNode(key) != null

    fun isEmpty(): Boolean = headNode == null

    fun size(): Int = size

    fun min(): Key? {
        if (size == 0) return null
        var minNode = headNode!!
        var node = headNode!!.next
        while (node != null) {
            if (node.pair.first < minNode.pair.first) minNode = node
            node = node.next
        }
        return minNode.pair.first
    }

    fun max(): Key? {
        if (size == 0) return null
        var maxNode = headNode!!
        var node = headNode!!.next
        while (node != null) {
            if (node.pair.first > maxNode.pair.first) maxNode = node
            node = node.next
        }
        return maxNode.pair.first
    }

    private fun floorWithCounter(key: Key): Pair<Int, Key?> {
        if (size == 0) return Pair(0, null)
        if (min() == null || key < min()!!) return Pair(0, null)
        var counter = 0
        var maxNode: Node<Key, Value>? = null
        var node = headNode
        while (node != null) {
            if (node.pair.first <= key) {
                counter++
                if (maxNode == null) maxNode = node
                else if (node.pair.first > maxNode.pair.first) maxNode = node
            }
            node = node.next
        }
        return Pair(counter, maxNode?.pair?.first)
    }

    fun floor(key: Key): Key? = floorWithCounter(key).second

    private fun ceilingWithCounter(key: Key): Pair<Int, Key?> {
        if (size == 0) return Pair(0, null)
        if (max() == null || key > max()!!) return Pair(0, null)
        var counter = 0
        var minNode: Node<Key, Value>? = null
        var node = headNode
        while (node != null) {
            if (node.pair.first >= key) {
                counter++
                if (minNode == null) minNode = node
                else if (node.pair.first < minNode.pair.first) minNode = node
            }
            node = node.next
        }
        return Pair(counter, minNode?.pair?.first)
    }

    fun ceiling(key: Key): Key? = ceilingWithCounter(key).second

    fun rank(key: Key): Int {
        val (counter, floor) = floorWithCounter(key)
        return if (floor == key) counter - 1 else counter
    }

    fun select(rank: Int): Key? {
        if (size == 0) return null
        if (rank > size || rank < 0) throw IllegalArgumentException(
            "rank must be greater than 0 and less than size of ST"
        )
        var node = headNode!!
        var prevNode: Node<Key, Value>
        val arr = Array(size) {
            if (node.next != null) {
                prevNode = node
                node = node.next!!
                prevNode
            } else node
        }
        return Quick.select(arr, rank, NodeComparator<Key, Value>()).pair.first
    }

    fun deleteMin() = delete(min())

    fun deleteMax() = delete(max())

    fun size(lo: Key, hi: Key): Int {
        if (hi < lo) throw IllegalArgumentException("hi key must be greater than lo key")
        val flrHi = floor(hi) ?: return 0
        if (ceiling(lo) == null) return 0
        return (if (hi > flrHi) rank(hi) else (rank(hi) + 1)) - rank(lo)
    }

    fun keys(): Iterable<Key> {
        val arr: Array<Node<Key, Value>> = if (headNode == null) arrayOf()
        else {
            var node = headNode!!
            var prevNode: Node<Key, Value>
            Array(size) {
                if (node.next != null) {
                    prevNode = node
                    node = node.next!!
                    prevNode
                } else node
            }
        }
        return arr.map { it.pair.first }.sorted()
    }

    fun keys(lo: Key, hi: Key): Iterable<Key> {
        if (hi < lo) throw IllegalArgumentException("hi key must be greater than lo key")
        return keys().filter { it in lo..hi }
    }

    private class NodeComparator<K : Comparable<K>, V> : Comparator<Node<K, V>> {
        override fun compare(o1: Node<K, V>, o2: Node<K, V>): Int {
            return when {
                o1.pair.first < o2.pair.first -> -1
                o1.pair.first == o2.pair.first -> 0
                else -> 1
            }
        }
    }
}