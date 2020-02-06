package lib.mst

import edu.princeton.cs.algs4.In
import lib.stackqueue.QueueLinkedList

class EdgeWeightedGraph(private val V: Int) {
    private val adj = Array(V) { QueueLinkedList<Edge>() }

    constructor(In: In) : this(In.readInt()) {
        val E = In.readInt()
        for (edge in 0..(E - 1)) addEdge(
            Edge(In.readInt(), In.readInt(), In.readDouble())
        )
    }

    fun addEdge(e: Edge) {
        val v = e.either()
        val w = e.other(v)
        adj[v].push(e)
        adj[w].push(e)
    }

    fun adj(v: Int): Iterable<Edge> = adj[v]
    fun V() = V
    fun E() = adj.fold(0) { acc, queue -> acc + queue.size } / 2
    fun edges(): Iterable<Edge> {
        val queue = QueueLinkedList<Edge>()
        for (node in adj.withIndex()) {
            for (edge in node.value) {
                val other = edge.other(node.index)
                if (other > node.index) queue.push(edge)
            }
        }
        return queue
    }
}