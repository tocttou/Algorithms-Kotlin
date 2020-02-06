package lib.unionfind

class QuickFind(N: Int) {
    // "N" array accesses
    private val id = IntArray(N) { it }

    // "2" array accesses
    fun connected(p: Int, q: Int): Boolean {
        return id[p] == id[q]
    }

    // "at most 2N + 2" array accesses
    fun union(p: Int, q: Int) {
        val pid = id[p]
        val qid = id[q]
        for ((index, i) in id.withIndex()) if (pid == i) id[index] = qid
    }
}