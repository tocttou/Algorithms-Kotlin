package lib.mst

import lib.priorityqueue.MinPQ
import lib.stackqueue.QueueLinkedList

class LazyPrimMST(G: EdgeWeightedGraph) {
    private val edges = QueueLinkedList<Edge>()
    private val marked = Array(G.V()) { false }
    private val minPQ = MinPQ<Edge>()
    private var weight = 0.0

    init {
        visit(G, 0)
        while (!minPQ.isEmpty() && edges.size < G.V() - 1) {
            val minWeightEdge = minPQ.delMin()
            val v = minWeightEdge.either()
            val w = minWeightEdge.other(v)
            if (marked[v] && marked[w]) continue
            edges.push(minWeightEdge)
            weight += minWeightEdge.weight()
            if (!marked[v]) visit(G, v)
            if (!marked[w]) visit(G, w)
        }
    }

    private fun visit(G: EdgeWeightedGraph, v: Int) {
        marked[v] = true
        for (edge in G.adj(v)) {
            val other = edge.other(v)
            if (!marked[other]) minPQ.insert(edge)
        }
    }

    fun edges(): Iterable<Edge> = edges
    fun weight() = weight
}