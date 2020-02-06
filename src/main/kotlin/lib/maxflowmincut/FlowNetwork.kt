package lib.maxflowmincut

import edu.princeton.cs.algs4.In
import lib.stackqueue.QueueLinkedList

class FlowNetwork(private val V: Int) {
    val adj = Array(V) { QueueLinkedList<FlowEdge>() }

    constructor(In: In) : this(In.readInt()) {
        val E = In.readInt()
        for (edge in 0..(E - 1)) addEdge(
            FlowEdge(In.readInt(), In.readInt(), In.readDouble())
        )
    }

    fun addEdge(e: FlowEdge) {
        val v = e.from()
        val w = e.to()
        adj[v].push(e)
        adj[w].push(e)
    }

    fun adj(v: Int): Iterable<FlowEdge> = adj[v]
    fun edges(): Iterable<FlowEdge> {
        val queue = QueueLinkedList<FlowEdge>()
        val marked = Array(V) { false }
        for (v in adj.indices) {
            for (e in adj[v]) {
                if (!marked[e.from()]) {
                    queue.push(e)
                }
            }
            marked[v] = true
        }
        return queue
    }

    fun V() = V
    fun E() = adj.fold(0) { acc, q -> acc + q.size } / 2
}