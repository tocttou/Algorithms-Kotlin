package lib.directedgraphs

import lib.stackqueue.QueueResizingArray
import lib.stackqueue.StackResizingArray

class DirectedBFP(G: UnweightedDigraph, private val s: Int) {
    private val marked = Array(G.V()) { false }
    private val edgeTo = Array(G.V()) { -1 }

    init {
        bfs(G, s)
    }

    private fun bfs(G: UnweightedDigraph, s: Int) {
        val queue = QueueResizingArray<Int>()
        queue.push(s)
        marked[s] = true
        while (!queue.isEmpty()) {
            val v = queue.pop()
            for (w in G.adj(v)) {
                if (!marked[w]) {
                    marked[w] = true
                    edgeTo[w] = v
                    queue.push(w)
                }
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