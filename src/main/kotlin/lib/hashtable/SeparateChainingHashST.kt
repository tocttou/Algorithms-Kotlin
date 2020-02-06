package lib.hashtable

class SeparateChainingHashST<Key, Value>(N: Int) {
    private val M = N / 5

    private val st = arrayOfNulls<Node<Key, Value>>(M)

    private data class Node<Key, Value>(
        val key: Key, var value: Value, var next: Node<Key, Value>?
    )

    private fun hash(key: Key): Int {
        return ((key ?: 0).hashCode() and 0x7fffffff) % M
    }

    fun get(key: Key): Value? {
        val i = hash(key)
        var x = st[i]
        while (x != null) {
            if (key == x.key) return x.value
            x = x.next
        }
        return null
    }

    fun put(key: Key, value: Value) {
        val i = hash(key)
        var x = st[i]
        var prev = st[i]
        while (x != null) {
            if (key == x.key) {
                x.value = value
                return
            }
            x = x.next
            if (x != null) prev = x
        }
        if (prev != null) prev.next = Node(key, value, null)
        else st[i] = Node(key, value, null)
    }
}