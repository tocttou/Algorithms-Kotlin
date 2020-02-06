package lib.mst

import lib.priorityqueue.MinPQ
import lib.stackqueue.QueueLinkedList
import lib.unionfind.WQUPCDoublePass

class KruskalMST(G: EdgeWeightedGraph) {
    private val uf = WQUPCDoublePass(G.V())
    private val edges = QueueLinkedList<Edge>()
    private var weight = 0.0

    init {
        val minPQ = MinPQ<Edge>()
        for (edge in G.edges()) minPQ.insert(edge)
        while (!minPQ.isEmpty() && edges.size < G.V() - 1) {
            val edge = minPQ.delMin()
            val v = edge.either()
            val w = edge.other(v)
            if (!uf.connected(v, w)) {
                edges.push(edge)
                weight += edge.weight()
                uf.union(v, w)
            }
        }
    }

    fun edges(): Iterable<Edge> = edges
    fun weight() = weight
}