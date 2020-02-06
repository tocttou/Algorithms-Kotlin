package lib.unionfind

class WQUPCDoublePass(N: Int) {
    private val id = IntArray(N) { it }
    private val weight = id.copyOf()

    private fun root(n: Int): Int {
        var i = n
        var j = n
        while (i != id[i]) i = id[i] // first pass sets i to root of i
        while (j != id[j]) {
            val jOld = j
            j = id[id[j]]
            id[jOld] = i
        } // second pass sets id of every other node to their root's id (id of i too)
        return i
    }

    fun connected(p: Int, q: Int): Boolean {
        return root(p) == root(q)
    }

    fun union(p: Int, q: Int) {
        val rootP = root(p)
        val rootQ = root(q)
        if (rootP == rootQ) return
        if (weight[rootP] < weight[rootQ]) {
            id[rootP] = id[rootQ]
            weight[rootQ] += weight[rootP]
        } else {
            id[rootQ] = id[rootP]
            weight[rootP] += weight[rootQ]
        }
    }
}