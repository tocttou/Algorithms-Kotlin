package lib.directedgraphs

import lib.shortestpath.DirectedEdge
import lib.shortestpath.EdgeWeightedDigraph
import lib.stackqueue.StackResizingArray

class TopologicalOrder<T>(G: Digraph<T>) {
    private val marked = Array(G.V()) { false }
    private val reversePostOrder = StackResizingArray<Int>()
    private var hasCycle = false

    init {
        for (v in 0..(G.V() - 1)) {
            if (!marked[v]) {
                dfs(G, v, StackResizingArray())
            }
        }
    }

    private fun dfs(G: Digraph<T>, v: Int, currentStack: StackResizingArray<Int>) {
        marked[v] = true
        currentStack.push(v)
        for (adjValue in G.adj(v)) {
            when (G) {
                is UnweightedDigraph -> {
                    val w = adjValue
                    if (marked[w as Int]) {
                        if (currentStack.contains(w)) hasCycle = true
                    } else {
                        dfs(G, w, currentStack)
                    }
                }
                is EdgeWeightedDigraph -> {
                    val other = (adjValue as DirectedEdge).to()
                    if (marked[other]) {
                        if (currentStack.contains(other)) hasCycle = true
                    } else {
                        dfs(G, other, currentStack)
                    }
                }
            }
        }
        currentStack.pop()
        reversePostOrder.push(v)
    }

    fun hasCycle() = hasCycle

    fun sort(): Iterable<Int>? = if (hasCycle) null else reversePostOrder
}