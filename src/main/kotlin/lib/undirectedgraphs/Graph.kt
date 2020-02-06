package lib.undirectedgraphs

import edu.princeton.cs.algs4.In
import lib.stackqueue.QueueLinkedList

class Graph(private val V: Int) {
    private val adj = Array(V) {
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
        adj[w].push(v)
    }

    fun adj(v: Int): Iterable<Int> = adj[v]

    fun V(): Int = V

    fun E(): Int = adj.fold(0) { acc, queue -> acc + queue.size } / 2
}