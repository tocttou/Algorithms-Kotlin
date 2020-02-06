package lib.elementarysymboltables

import lib.stackqueue.QueueResizingArray

class BST<Key : Comparable<Key>, Value> {
    private data class Node<Key, Value>(
        val key: Key,
        var value: Value,
        var left: Node<Key, Value>?,
        var right: Node<Key, Value>?,
        var count: Int
    )

    private var root: Node<Key, Value>? = null

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
    }

    private fun put(node: Node<Key, Value>?, key: Key, value: Value): Node<Key, Value> {
        if (node == null) return Node(key, value, null, null, 1)
        when {
            key < node.key -> node.left = put(node.left, key, value)
            key > node.key -> node.right = put(node.right, key, value)
            else -> node.value = value
        }
        node.count = 1 + size(node.left) + size(node.right)
        return node
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
        root = if (root != null) deleteMin(root!!) else null
    }

    private fun deleteMin(node: Node<Key, Value>): Node<Key, Value>? {
        if (node.left == null) return node.right
        node.left = deleteMin(node.left!!)
        node.count = 1 + size(node.left) + size(node.right)
        return node
    }

    fun deleteMax() {
        root = if (root != null) deleteMax(root!!) else null
    }

    private fun deleteMax(node: Node<Key, Value>): Node<Key, Value>? {
        if (node.right == null) return node.left
        node.right = deleteMax(node.right!!)
        node.count = 1 + size(node.left) + size(node.right)
        return node
    }

    fun delete(key: Key) {
        root = delete(root, key)
    }

    private fun delete(node: Node<Key, Value>?, key: Key): Node<Key, Value>? {
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
                x.count = 1 + size(x.left) + size(x.right)
                return x
            }
        }
        node.count = 1 + size(node.left) + size(node.right)
        return node
    }
}