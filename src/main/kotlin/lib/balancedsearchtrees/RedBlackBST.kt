package lib.balancedsearchtrees

import lib.stackqueue.QueueResizingArray

class RedBlackBST<Key : Comparable<Key>, Value> {
    private val RED = true
    private val BLACK = false

    private data class Node<Key, Value>(
        var key: Key,
        var value: Value,
        var left: Node<Key, Value>?,
        var right: Node<Key, Value>?,
        var color: Boolean,
        var count: Int
    )

    private var root: Node<Key, Value>? = null

    private fun isRed(x: Node<Key, Value>?): Boolean {
        return if (x == null) false else x.color == RED
    }

    fun contains(key: Key) = contains(root, key) != null

    fun isEmpty() = root == null

    private fun contains(node: Node<Key, Value>?, key: Key): Node<Key, Value>? {
        if (node == null) return null
        return when {
            key == node.key -> node
            key < node.key -> contains(node.left, key)
            else -> contains(node.right, key)
        }
    }

    fun put(key: Key, value: Value) {
        root = put(root, key, value)
        (root as Node<Key, Value>).color = BLACK
    }

    private fun put(node: Node<Key, Value>?, key: Key, value: Value): Node<Key, Value> {
        if (node == null) return Node(key, value, null, null, RED, 1)
        var h = node
        when {
            key < h.key -> h.left = put(h.left, key, value)
            key > h.key -> h.right = put(h.right, key, value)
            else -> h.value = value
        }

        if (isRed(h.right) && !isRed(h.left)) h = rotateLeft(h)
        if (isRed(h.left) && isRed(h.left!!.left)) h = rotateRight(h)
        if (isRed(h.left) && isRed(h.right)) flipColors(h)
        h.count = 1 + size(h.left) + size(h.right)

        return h
    }

    private fun rotateRight(node: Node<Key, Value>): Node<Key, Value> {
        val x = node.left!!
        node.left = x.right
        x.right = node
        x.color = x.right!!.color
        x.right!!.color = RED
        x.count = node.count
        node.count = 1 + size(node.left) + size(node.right)
        return x
    }

    private fun rotateLeft(node: Node<Key, Value>): Node<Key, Value> {
        val x = node.right!!
        node.right = x.left
        x.left = node
        x.color = x.left!!.color
        x.left!!.color = RED
        x.count = node.count
        node.count = 1 + size(node.left) + size(node.right)
        return x
    }

    private fun flipColors(node: Node<Key, Value>) {
        node.color = !node.color
        node.left!!.color = !node.left!!.color
        node.right!!.color = !node.right!!.color
    }

    private fun moveRedLeft(node: Node<Key, Value>): Node<Key, Value> {
        var h = node
        flipColors(h)
        if (isRed(h.right!!.left)) {
            h.right = rotateRight(h.right!!)
            h = rotateLeft(h)
            flipColors(h)
        }
        return h
    }

    private fun moveRedRight(node: Node<Key, Value>): Node<Key, Value> {
        var h = node
        flipColors(h)
        if (isRed(h.left!!.left)) {
            h = rotateRight(h)
            flipColors(h)
        }
        return h
    }

    private fun balance(node: Node<Key, Value>): Node<Key, Value> {
        var h = node
        if (isRed(h.right)) h = rotateLeft(h)
        if (isRed(h.left) && isRed(h.left!!.left)) h = rotateRight(h)
        if (isRed(h.left) && isRed(h.right)) flipColors(h)

        h.count = 1 + size(h.left) + size(h.right)
        return h
    }

    fun get(key: Key): Value? {
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

    fun min(): Key? = min(root)?.key

    private fun min(node: Node<Key, Value>?): Node<Key, Value>? {
        var currentNode = node
        while (currentNode?.left != null) currentNode = currentNode.left
        return currentNode
    }

    fun max(): Key? {
        var currentNode = root
        while (currentNode?.right != null) currentNode = currentNode.right
        return currentNode?.key
    }

    fun floor(key: Key): Key? = floor(root, key)?.key

    private fun floor(node: Node<Key, Value>?, key: Key): Node<Key, Value>? {
        if (node == null) return null
        return when {
            key == node.key -> node
            key < node.key -> floor(node.left, key)
            else -> floor(node.right, key) ?: node
        }
    }

    fun ceiling(key: Key): Key? = ceiling(root, key)?.key

    private fun ceiling(node: Node<Key, Value>?, key: Key): Node<Key, Value>? {
        if (node == null) return null
        return when {
            key == node.key -> node
            key > node.key -> ceiling(node.right, key)
            else -> ceiling(node.left, key) ?: node
        }
    }

    fun size() = size(root)

    fun size(lo: Key, hi: Key): Int {
        if (hi < lo) throw IllegalArgumentException("hi key must be greater than lo key")
        if (min() == null || hi < min()!!) return 0
        if (max() == null || lo > max()!!) return 0
        return (if (hi > floor(hi)!!) rank(hi) else (rank(hi) + 1)) - rank(lo)
    }

    private fun size(node: Node<Key, Value>?): Int = node?.count ?: 0

    fun rank(key: Key) = rank(root, key)

    private fun rank(node: Node<Key, Value>?, key: Key): Int {
        if (node == null) return 0
        return when {
            key == node.key -> size(node.left)
            key < node.key -> rank(node.left, key)
            else -> 1 + size(node.left) + rank(node.right, key)
        }
    }

    fun keys(): Iterable<Key> {
        val queue = QueueResizingArray<Key>()
        inorder(root, queue)
        return queue
    }

    fun keys(lo: Key, hi: Key): Iterable<Key> {
        if (hi < lo) throw IllegalArgumentException("hi key must be greater than lo key")
        val queue = QueueResizingArray<Key>()
        searchAndEnqueue(root, queue, lo, hi)
        return queue
    }

    private fun searchAndEnqueue(
        node: Node<Key, Value>?, queue: QueueResizingArray<Key>, lo: Key, hi: Key
    ) {
        if (node == null) return
        searchAndEnqueue(node.left, queue, lo, hi)
        if (node.key in lo..hi) queue.push(node.key)
        searchAndEnqueue(node.right, queue, lo, hi)
    }

    private fun inorder(node: Node<Key, Value>?, queue: QueueResizingArray<Key>) {
        if (node == null) return
        inorder(node.left, queue)
        queue.push(node.key)
        inorder(node.right, queue)
    }

    fun select(rank: Int): Key? {
        if (rank > size() || rank < 0) throw IllegalArgumentException(
            "rank must be greater than 0 and less than size of ST"
        )
        return select(root, rank)?.key
    }

    private fun select(node: Node<Key, Value>?, rank: Int): Node<Key, Value>? {
        if (node == null) return null
        return when {
            rank == node.left?.count ?: 0 -> node
            rank < node.left?.count ?: 0 -> select(node.left, rank)
            else -> select(node.right, rank - (node.left?.count ?: 0) - 1)
        }
    }

    fun deleteMin() {
        root = if (root != null) {
            if (!isRed(root!!.left) && !isRed(root!!.right)) root!!.color = RED
            deleteMin(root!!)
        } else null
        if (!isEmpty()) root!!.color = BLACK
    }

    private fun deleteMin(node: Node<Key, Value>): Node<Key, Value>? {
        if (node.left == null) return null
        var h = node

        if (!isRed(h.left) && !isRed(h.left!!.left)) h = moveRedLeft(h)

        h.left = deleteMin(h.left!!)
        return balance(h)
    }

    fun deleteMax() {
        root = if (root != null) {
            if (!isRed(root!!.left) && !isRed(root!!.right)) root!!.color = RED
            deleteMax(root!!)
        } else null
        if (!isEmpty()) root!!.color = BLACK
    }

    private fun deleteMax(node: Node<Key, Value>): Node<Key, Value>? {
        var h = node
        if (isRed(h.left)) h = rotateRight(h)

        if (h.right == null) return null

        if (!isRed(h.right) && !isRed(h.right!!.left)) h = moveRedRight(h)

        h.right = deleteMax(h.right!!)

        return balance(h)
    }

    fun delete(key: Key) {
        if (!isRed(root!!.left) && !isRed(root!!.right)) root!!.color = RED
        root = delete(root, key)
        if (!isEmpty()) root!!.color = BLACK
    }

    private fun delete(node: Node<Key, Value>?, key: Key): Node<Key, Value>? {
        if (node == null) return null
        var h = node
        if (key < h.key) {
            if (!isRed(h.left) && !isRed(h.left!!.left)) h = moveRedLeft(h)
            h.left = delete(h.left, key)
        } else {
            if (isRed(h.left)) h = rotateRight(h)
            if (key.compareTo(h.key) == 0 && h.right == null) return null
            if (!isRed(h.right) && !isRed(h.right!!.left)) h = moveRedRight(h)
            if (key.compareTo(h.key) == 0) {
                val x = min(h.right)
                h.key = x!!.key
                h.value = x.value
                h.right = deleteMin(h.right!!)
            } else h.right = delete(h.right, key)
        }
        return balance(h)
    }
}