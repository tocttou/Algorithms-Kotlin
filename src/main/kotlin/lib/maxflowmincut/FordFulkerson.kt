package lib.maxflowmincut

import lib.stackqueue.QueueLinkedList

class FordFulkerson(G: FlowNetwork, s: Int, t: Int) {
    private lateinit var marked: Array<Boolean>
    private lateinit var edgeTo: Array<FlowEdge?>
    private var value = 0.0

    init {
        while (hasAugmentingPath(G, s, t)) {
            var bottle = Double.POSITIVE_INFINITY
            var v = t
            while (v != s) {
                bottle = minOf(bottle, edgeTo[v]!!.residualCapacityTo(v))
                v = edgeTo[v]!!.other(v)
            }
            v = t
            while (v != s) {
                edgeTo[v]!!.addResidualFlow(v, bottle)
                v = edgeTo[v]!!.other(v)
            }
            value += bottle
        }
    }

    private fun hasAugmentingPath(G: FlowNetwork, s: Int, t: Int): Boolean {
        marked = Array(G.V()) { false }
        edgeTo = Array(G.V()) { null }
        val queue = QueueLinkedList<Int>()
        queue.push(s)
        marked[s] = true
        while (!queue.isEmpty()) {
            val v = queue.pop()
            for (e in G.adj(v)) {
                val w = e.other(v)
                if (e.residualCapacityTo(w) > 0 && !marked[w]) {
                    edgeTo[w] = e
                    marked[w] = true
                    queue.push(w)
                }
            }
        }
        return marked[t]
    }

    fun value() = value
    fun inCut(v: Int) = marked[v]
}