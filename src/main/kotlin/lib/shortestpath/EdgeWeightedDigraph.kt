package lib.shortestpath

import edu.princeton.cs.algs4.In
import lib.directedgraphs.Digraph
import lib.stackqueue.QueueLinkedList

class EdgeWeightedDigraph(private val V: Int) : Digraph<DirectedEdge>() {
    override val adj = Array(V) { QueueLinkedList<DirectedEdge>() }

    constructor(In: In) : this(In.readInt()) {
        val E = In.readInt()
        for (edge in 0..(E - 1)) addEdge(
            DirectedEdge(In.readInt(), In.readInt(), In.readDouble())
        )
    }

    fun addEdge(e: DirectedEdge) {
        val v = e.from()
        adj[v].push(e)
    }

    override fun adj(v: Int): Iterable<DirectedEdge> = adj[v]
    override fun V() = V
    override fun E() = adj.fold(0) { acc, queue -> acc + queue.size }
    fun edges(): Iterable<DirectedEdge> {
        val queue = QueueLinkedList<DirectedEdge>()
        adj.asSequence().flatMap { it.asSequence() }.forEach { queue.push(it) }
        return queue
    }
}