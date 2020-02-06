package lib.shortestpath

import lib.directedgraphs.TopologicalOrder

/*
Requires: acyclic graph
 */
class AcyclicSP(G: EdgeWeightedDigraph, s: Int) : SP() {
    override val distTo = Array(G.V()) { Double.POSITIVE_INFINITY }
    override val edgeTo = Array<DirectedEdge?>(G.V()) { null }

    init {
        distTo[s] = 0.0
        val topologicalOrder = TopologicalOrder(G)
        if (topologicalOrder.hasCycle()) throw IllegalArgumentException("graph should not have cycle")
        val order = topologicalOrder.sort()!!
        order.flatMap { G.adj(it) }.forEach { relax(it) }
    }

    override fun relax(directedEdge: DirectedEdge) {
        val v = directedEdge.from()
        val w = directedEdge.to()
        if (compareValues(distTo[v] + directedEdge.weight(), distTo[w]) < 0) {
            distTo[w] = distTo[v] + directedEdge.weight()
            edgeTo[w] = directedEdge
        }
    }
}