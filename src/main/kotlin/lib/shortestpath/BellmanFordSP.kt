package lib.shortestpath

import edu.princeton.cs.algs4.Queue

/*
Requires: No -ve cycle
 */
class BellmanFordSP(private val G: EdgeWeightedDigraph, s: Int) : SP() {
    override val distTo = Array(G.V()) { Double.POSITIVE_INFINITY }
    override val edgeTo = Array<DirectedEdge?>(G.V()) { null }
    private val changed = Array(G.V()) { true }
    private var cycleAt = -1

    init {
        distTo[s] = 0.0
        for (pass in 0..(G.V() - 1)) {
            val oldDistTo = distTo.clone()
            for (v in 0..(G.V() - 1)) {
                for (directedEdge in G.adj(v)) {
                    if (changed[v]) relax(directedEdge)
                }
            }
            for (oldDist in oldDistTo.withIndex()) {
                if (compareValues(oldDist.value, distTo[oldDist.index]) == 0) {
                    changed[oldDist.index] = false
                } else {
                    if (cycleAt == -1) {
                        if (pass == oldDist.index) cycleAt = oldDist.index
                    }
                }
            }
        }
    }

    override fun relax(directedEdge: DirectedEdge) {
        val v = directedEdge.from()
        val w = directedEdge.to()
        if (compareValues(distTo[v] + directedEdge.weight(), distTo[w]) < 0) {
            distTo[w] = distTo[v] + directedEdge.weight()
            edgeTo[w] = directedEdge
        }
    }

    fun hasNegativeCycle() = cycleAt > -1
    fun negativeCycle(): Iterable<DirectedEdge>? {
        if (!hasNegativeCycle()) return null
        val queue = Queue<DirectedEdge>()
        val marked = Array(G.V()) { false }
        var currentNode = cycleAt
        while (edgeTo[currentNode] != null && !marked[currentNode]) {
            marked[currentNode] = true
            currentNode = edgeTo[currentNode]!!.from()
        }
        while (edgeTo[currentNode] != null) {
            queue.enqueue(edgeTo[currentNode])
            currentNode = edgeTo[currentNode]!!.from()
            if (currentNode == queue.peek().to()) break
        }
        return queue
    }
}