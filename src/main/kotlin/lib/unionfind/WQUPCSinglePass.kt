package lib.unionfind

class WQUPCSinglePass(N: Int) {
    private val id = IntArray(N) { it }
    private val weight = id.copyOf()

    private fun root(n: Int): Int {
        var i = n
        while (i != id[i]) {
            // setting the id of parent to id of grandparent (because root is same)
            // effectively skips the grandparent
            id[i] = id[id[i]]
            i = id[i]
        }
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
