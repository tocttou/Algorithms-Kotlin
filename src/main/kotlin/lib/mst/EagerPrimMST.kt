package lib.mst

import edu.princeton.cs.algs4.IndexMinPQ
import lib.stackqueue.QueueLinkedList

class EagerPrimMST(G: EdgeWeightedGraph) {
    private val indexMinPQ = IndexMinPQ<Double>(G.V() + 1)
    private val edgeTo = Array<Edge?>(G.V()) { null }
    private val marked = Array(G.V()) { false }
    private val edges = QueueLinkedList<Edge>()
    private var weight = 0.0

    init {
        visit(G, 0)
        marked[0] = true

        while (!indexMinPQ.isEmpty && edges.size < G.V() - 1) {
            val nearestNode = indexMinPQ.delMin() - 1
            edges.push(edgeTo[nearestNode]!!)
            marked[nearestNode] = true
            weight += edgeTo[nearestNode]!!.weight()
            visit(G, nearestNode)
        }
    }

    private fun visit(G: EdgeWeightedGraph, v: Int) {
        val connectedEdges = G.adj(v)
        for (edge in connectedEdges) {
            val other = edge.other(v)
            if (!marked[other]) {
                if (!indexMinPQ.contains(other + 1)) {
                    indexMinPQ.insert(other + 1, edge.weight())
                    edgeTo[other] = edge
                } else {
                    val weightOnPQ = indexMinPQ.keyOf(other + 1)
                    if (edge.weight() < weightOnPQ) {
                        indexMinPQ.decreaseKey(other + 1, edge.weight())
                        edgeTo[other] = edge
                    }
                }
            }
        }
    }

    fun edges(): Iterable<Edge> = edges
    fun weight() = weight
}