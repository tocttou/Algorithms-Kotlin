package lib.undirectedgraphs

import lib.stackqueue.StackResizingArray

class DepthFirstPaths(G: Graph, private val s: Int) {
    private val marked = Array(G.V()) { false }
    private val edgeTo = Array(G.V()) { -1 }

    init {
        dfs(G, s)
    }

    private fun dfs(G: Graph, v: Int) {
        marked[v] = true
        for (w in G.adj(v)) {
            if (!marked[w]) {
                dfs(G, w)
                edgeTo[w] = v
            }
        }
    }

    fun hasPathTo(v: Int): Boolean = marked[v]

    fun pathTo(v: Int): Iterable<Int>? {
        if (!hasPathTo(v)) return null
        val path = StackResizingArray<Int>()
        var x = v
        while (x != s) {
            path.push(x)
            x = edgeTo[x]
        }
        path.push(s)
        return path
    }
}