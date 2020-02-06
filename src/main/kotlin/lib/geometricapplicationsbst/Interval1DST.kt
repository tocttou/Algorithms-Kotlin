package lib.geometricapplicationsbst

import lib.stackqueue.QueueResizingArray

class Interval1DST<Key : Comparable<Key>> {
    data class Interval<Key : Comparable<Key>>(val lo: Key, val hi: Key) :
        Comparable<Interval<Key>> {
        override fun compareTo(other: Interval<Key>): Int = lo.compareTo(other.lo)

        fun intersects(other: Interval<Key>): Boolean {
            val forwardSegment = if (other.lo >= lo) other else this
            return forwardSegment.lo < hi
        }
    }

    private data class Node<Key : Comparable<Key>, Value>(
        val key: Interval<Key>,
        var value: Value,
        var left: Node<Key, Value>?,
        var right: Node<Key, Value>?
    )

    private var root: Node<Key, Key>? = null

    fun contains(lo: Key, hi: Key) = contains(root, Interval(lo, hi)) != null

    private fun contains(node: Node<Key, Key>?, key: Interval<Key>): Node<Key, Key>? {
        if (node == null) return null
        return when {
            key == node.key -> node
            key < node.key -> contains(node.left, key)
            else -> contains(node.right, key)
        }
    }

    fun put(lo: Key, hi: Key) {
        root = put(root, Interval(lo, hi), hi)
    }

    private fun put(node: Node<Key, Key>?, key: Interval<Key>, value: Key): Node<Key, Key> {
        if (node == null) return Node(key, value, null, null)
        when {
            key < node.key -> node.left = put(node.left, key, value)
            key > node.key -> node.right = put(node.right, key, value)
            else -> node.value = value
        }
        if (value > node.value) node.value = value
        return node
    }

    fun get(lo: Key, hi: Key): Key? = get(Interval(lo, hi))

    private fun get(key: Interval<Key>): Key? {
        var currentNode = root
        while (currentNode != null) {
            when {
                key < currentNode.key -> currentNode = currentNode.left
                key > currentNode.key -> currentNode = currentNode.right
                else -> return currentNode.value
            }
        }
        return currentNode?.value
    }

    fun delete(lo: Key, hi: Key) {
        root = delete(root, Interval(lo, hi))
    }

    fun intersects(lo: Key, hi: Key): Iterable<Interval<Key>> {
        if (lo >= hi) throw IllegalArgumentException("lo must be greater than hi")
        val queue = QueueResizingArray<Interval<Key>>()
        val query = Interval(lo, hi)
        var x = root
        while (x != null) {
            if (x.key.intersects(query)) queue.push(x.key)
            if (x.left == null) x = x.right
            else if ((x.left as Node).value < lo) x = x.right
            else x = x.left
        }
        return queue
    }

    fun intersectSize(lo: Key, hi: Key): Int {
        if (lo >= hi) throw IllegalArgumentException("lo must be less than hi")
        var counter = 0
        val query = Interval(lo, hi)
        var x = root
        while (x != null) {
            if (x.key.intersects(query)) counter++
            if (x.left == null) x = x.right
            else if ((x.left as Node).value < lo) x = x.right
            else x = x.left
        }
        return counter
    }

    private fun delete(node: Node<Key, Key>?, key: Interval<Key>): Node<Key, Key>? {
        if (node == null) return null
        when {
            key < node.key -> node.left = delete(node.left, key)
            key > node.key -> node.right = delete(node.right, key)
            else -> {
                if (node.left == null) return node.right
                if (node.right == null) return node.left

                val x = min(node.right)!!
                x.left = node.left
                x.right = deleteMin(node.right!!)
                updateMaxValue(x)
                return x
            }
        }
        updateMaxValue(node)
        return node
    }

    @Suppress("UNCHECKED_CAST")
    private fun updateMaxValue(node: Node<Key, Key>) {
        val maxVal = when {
            node.left != null && node.right == null -> (node.left as Node<Interval<Key>, Key>).value
            node.right != null && node.left == null -> (node.right as Node<Interval<Key>, Key>).value
            node.right != null && node.left != null -> maxOf(
                (node.left as Node<Interval<Key>, Key>).value,
                (node.right as Node<Interval<Key>, Key>).value
            )
            else -> null
        }
        if (maxVal != null) {
            node.value = if (node.key.hi < maxVal) maxVal else node.key.hi
        } else node.value = node.key.hi
    }

    private fun min(node: Node<Key, Key>?): Node<Key, Key>? {
        var currentNode = node
        while (currentNode?.left != null) currentNode = currentNode.left
        return currentNode
    }

    private fun deleteMin(node: Node<Key, Key>): Node<Key, Key>? {
        if (node.left == null) return node.right
        node.left = deleteMin(node.left!!)
        return node
    }
}