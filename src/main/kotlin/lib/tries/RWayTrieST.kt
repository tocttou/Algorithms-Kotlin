package lib.tries

import lib.stackqueue.QueueLinkedList

class RWayTrieST<Value> {
    private val R = 256
    private var root = Node<Value>(null, arrayOfNulls(R))

    private data class Node<Value>(var value: Value?, var next: Array<Node<Value>?>)

    fun contains(key: String) = get(key) != null
    fun get(key: String): Value? {
        val x = get(root, key, 0) ?: return null
        return x.value
    }

    private fun get(x: Node<Value>?, key: String, d: Int): Node<Value>? {
        if (x == null) return null
        if (d == key.length) return x
        val c = key[d].toInt()
        return get(x.next[c], key, d + 1)
    }

    fun put(key: String, value: Value) {
        root = put(root, key, value, 0)
    }

    private fun put(_x: Node<Value>?, key: String, value: Value, d: Int): Node<Value> {
        var x = _x
        if (x == null) x = Node(null, arrayOfNulls(R))
        if (d == key.length) {
            x.value = value
            return x
        }
        val c = key[d]
        x.next[c.toInt()] = put(x.next[c.toInt()], key, value, d + 1)
        return x
    }

    fun keys(): Iterable<String> {
        val queue = QueueLinkedList<String>()
        collect(root, "", queue)
        return queue
    }

    fun keysWithPrefix(prefix: String): Iterable<String> {
        val queue = QueueLinkedList<String>()
        val x = get(root, prefix, 0)
        collect(x, prefix, queue)
        return queue
    }

    fun longestPrefixOf(query: String): String {
        val length = search(root, query, 0, 0)
        return query.substring(0, length)
    }

    private fun search(x: Node<Value>?, query: String, d: Int, _length: Int): Int {
        var length = _length
        if (x == null) return length
        if (x.value != null) length = d
        if (d == query.length) return length
        val c = query[d]
        return search(x.next[c.toInt()], query, d + 1, length)
    }

    private fun collect(x: Node<Value>?, prefix: String, q: QueueLinkedList<String>) {
        if (x == null) return
        if (x.value != null) q.push(prefix)
        for (c in 0 until R) collect(x.next[c], prefix + c.toChar(), q)
    }
}