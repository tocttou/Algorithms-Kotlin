package lib.directedgraphs

import lib.stackqueue.QueueResizingArray
import lib.stackqueue.StackResizingArray

class MultiSourceShortestPathBFP(G: UnweightedDigraph, private val s: Array<Int>) {
    private val marked = Array(G.V()) { false }
    private val edgeTo = Array(G.V()) { -1 }

    init {
        bfs(G, s)
    }

    private fun bfs(G: UnweightedDigraph, s: Array<Int>) {
        val queue = QueueResizingArray<Int>()
        for (e in s) {
            queue.push(e)
            marked[e] = true
        }
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
        while (!s.contains(x)) {
            path.push(x)
            x = edgeTo[x]
        }
        path.push(x)
        return path
    }
}