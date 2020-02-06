package lib.directedgraphs

import edu.princeton.cs.algs4.In
import lib.stackqueue.QueueLinkedList

class UnweightedDigraph(private val V: Int) : Digraph<Int>() {
    override val adj = Array(V) {
        QueueLinkedList<Int>()
    }

    constructor(In: In) : this(In.readInt()) {
        val numEdges = In.readInt()
        for (i in 0..(numEdges - 1)) {
            val v = In.readInt()
            val w = In.readInt()
            addEdge(v, w)
        }
    }

    fun addEdge(v: Int, w: Int) {
        adj[v].push(w)
    }

    override fun adj(v: Int): Iterable<Int> = adj[v]

    override fun V(): Int = V

    override fun E(): Int = adj.fold(0) { acc, queue -> acc + queue.size }

    fun reverse(): UnweightedDigraph {
        val graph = UnweightedDigraph(V())
        for (v in 0..(V() - 1)) {
            for (w in adj(v)) {
                graph.addEdge(w, v)
            }
        }
        return graph
    }
}