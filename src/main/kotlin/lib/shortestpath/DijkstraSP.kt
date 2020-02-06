package lib.shortestpath

import edu.princeton.cs.algs4.IndexMinPQ

/*
Requires: non-negative weights
 */
class DijkstraSP(G: EdgeWeightedDigraph, s: Int) : SP() {
    override val distTo = Array(G.V()) { Double.POSITIVE_INFINITY }
    override val edgeTo = Array<DirectedEdge?>(G.V()) { null }
    private val indexMinPQ = IndexMinPQ<Double>(G.V())

    init {
        distTo[s] = 0.0
        indexMinPQ.insert(s, distTo[s])
        while (!indexMinPQ.isEmpty) {
            val nearestNode = indexMinPQ.delMin()
            for (directedEdge in G.adj(nearestNode)) relax(directedEdge)
        }
    }

    override fun relax(directedEdge: DirectedEdge) {
        val v = directedEdge.from()
        val w = directedEdge.to()
        if (compareValues(distTo[v] + directedEdge.weight(), distTo[w]) < 0) {
            distTo[w] = distTo[v] + directedEdge.weight()
            edgeTo[w] = directedEdge
            if (indexMinPQ.contains(w)) indexMinPQ.decreaseKey(w, distTo[w])
            else indexMinPQ.insert(w, distTo[w])
        }
    }
}