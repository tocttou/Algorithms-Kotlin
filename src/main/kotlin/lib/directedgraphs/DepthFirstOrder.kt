package lib.directedgraphs

import lib.stackqueue.StackResizingArray

class DepthFirstOrder(G: UnweightedDigraph) {
    private val marked = Array(G.V()) { false }
    private val reversePostOrder = StackResizingArray<Int>()

    init {
        for (v in 0..(G.V() - 1)) if (!marked[v]) dfs(G, v)
    }

    private fun dfs(G: UnweightedDigraph, v: Int) {
        marked[v] = true
        for (w in G.adj(v)) if (!marked[w]) dfs(G, w)
        reversePostOrder.push(v)
    }

    fun reversePost(): Iterable<Int> = reversePostOrder
}