package lib.directedgraphs

class KosarajuSharirSCC(G: UnweightedDigraph) {
    private val marked = Array(G.V()) { false }
    private val id = Array(G.V()) { -1 }
    private var count = 0

    init {
        val dfo = DepthFirstOrder(G.reverse())
        for (v in dfo.reversePost()) {
            if (!marked[v]) {
                dfs(G, v)
                count++
            }
        }
    }

    private fun dfs(G: UnweightedDigraph, v: Int) {
        marked[v] = true
        id[v] = count
        for (w in G.adj(v)) if (!marked[w]) dfs(G, w)
    }

    fun count() = count
    fun id(v: Int) = id[v]
    fun connected(v: Int, w: Int) = id(v) == id(w)
}