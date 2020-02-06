package lib.tries

import lib.stackqueue.QueueLinkedList

class TST<Value> {
    private data class Node<Value>(
        var value: Value? = null,
        var c: Char = ' ',
        var left: Node<Value>? = null,
        var mid: Node<Value>? = null,
        var right: Node<Value>? = null
    )

    private var root: Node<Value>? = null

    fun put(key: String, `val`: Value) {
        root = put(root, key, `val`, 0)
    }

    private fun put(_x: Node<Value>?, key: String, value: Value, d: Int): Node<Value> {
        var x = _x
        val c = key[d]
        if (x == null) {
            x = Node()
            x.c = c
        }
        when {
            c < x.c -> x.left = put(x.left, key, value, d)
            c > x.c -> x.right = put(x.right, key, value, d)
            d < key.length - 1 -> x.mid = put(x.mid, key, value, d + 1)
            else -> x.value = value
        }
        return x
    }

    fun contains(key: String) = get(key) != null
    fun get(key: String): Value? {
        val x = get(root, key, 0) ?: return null
        return x.value
    }

    private fun get(x: Node<Value>?, key: String, d: Int): Node<Value>? {
        if (x == null) return null
        val c = key[d]
        return when {
            c < x.c -> get(x.left, key, d)
            c > x.c -> get(x.right, key, d)
            d < key.length - 1 -> get(x.mid, key, d + 1)
            else -> x
        }
    }

    fun keys(): Iterable<String> {
        val queue = QueueLinkedList<String>()
        collect(root, "", queue)
        return queue
    }

    fun keysWithPrefix(prefix: String): Iterable<String> {
        val queue = QueueLinkedList<String>()
        val x = get(root, prefix, 0) ?: return queue
        collect(x.mid, prefix, queue)
        return queue
    }

    fun longestPrefixOf(query: String): String {
        val length = search(root, query, 0, 0)
        return query.substring(0, length)
    }

    private fun search(x: Node<Value>?, query: String, d: Int, _length: Int): Int {
        var length = _length
        if (x == null) return length
        val c = query[d]
        if (x.value != null && c == x.c) length = d + 1
        if (d == query.length) return length
        return when {
            c < x.c -> search(x.left, query, d, length)
            c == x.c -> search(x.mid, query, d + 1, length)
            else -> search(x.right, query, d, length)
        }
    }

    private fun collect(x: Node<Value>?, prefix: String, q: QueueLinkedList<String>) {
        if (x == null) return
        if (x.value != null) q.push(prefix + x.c)
        if (x.left != null) collect(x.left, prefix, q)
        if (x.mid != null) collect(x.mid, prefix + x.c, q)
        if (x.right != null) collect(x.right, prefix, q)
    }
}