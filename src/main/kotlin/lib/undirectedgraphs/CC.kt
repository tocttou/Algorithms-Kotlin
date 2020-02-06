package lib.undirectedgraphs

class CC(G: Graph) {
    private val marked = Array(G.V()) { false }
    private val id = Array(G.V()) { -1 }
    private var count = 0

    init {
        for (v in 0..(G.V() - 1)) {
            if (!marked[v]) {
                dfs(G, v)
                count++
            }
        }
    }

    private fun dfs(G: Graph, v: Int) {
        marked[v] = true
        id[v] = count
        for (w in G.adj(v)) if (!marked[w]) dfs(G, w)
    }

    fun count() = count
    fun id(v: Int) = id[v]
    fun connected(v: Int, w: Int) = id(v) == id(w)
}