package lib.unionfind

class QuickUnion(N: Int) {
    // "N" array accesses
    private val id = IntArray(N) { it }

    // "depth of n" array accesses
    private fun root(n: Int): Int {
        var i = n
        while (i != id[i]) i = id[i]
        return i
    }

    // "depth of p and q" array accesses
    fun connected(p: Int, q: Int): Boolean {
        return root(p) == root(q)
    }

    // "depth of p and q" array accesses
    fun union(p: Int, q: Int) {
        val rootP = root(p)
        val rootQ = root(q)
        id[rootP] = id[rootQ]
    }
}