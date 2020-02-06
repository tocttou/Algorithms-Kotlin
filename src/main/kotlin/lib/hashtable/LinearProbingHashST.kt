package lib.hashtable

class LinearProbingHashST<Key, Value>(N: Int) {
    private var M = 2 * N
    private val values: Array<in Any?> = Array(M, { null })
    private val keys: Array<in Any?> = Array(M, { null })

    private fun hash(key: Key): Int {
        return ((key ?: 0).hashCode() and 0x7fffffff) % M
    }

    fun put(key: Key, value: Value) {
        var i = hash(key)
        while (keys[i] != null) {
            if (keys[i] == key) break
            i = (i + 1) % M
        }
        keys[i] = key
        values[i] = value
    }

    @Suppress("UNCHECKED_CAST")
    fun get(key: Key): Value? {
        var i = hash(key)
        while (keys[i] != null) {
            if (keys[i] == key) return values[i] as Value
            i = (i + 1) % M
        }
        return null
    }
}