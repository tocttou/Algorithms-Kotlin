package lib.shortestpath.applications

import edu.princeton.cs.algs4.In
import lib.shortestpath.BellmanFordSP
import lib.shortestpath.DirectedEdge
import lib.shortestpath.EdgeWeightedDigraph

class ArbitrageDetection(filename: String) {
    private val edgeWeightedDigraph: EdgeWeightedDigraph
    private val bellmanFordSP: BellmanFordSP

    init {
        val `in` = In(filename)
        val all = `in`.readAll()
        `in`.close()
        val rows = all.split("\n")
        edgeWeightedDigraph = EdgeWeightedDigraph(rows.size)
        for (row in rows.withIndex()) {
            val split = row.value.split(" ")
            split.withIndex().asSequence().filter { row.index != it.index }.forEach {
                edgeWeightedDigraph.addEdge(
                    DirectedEdge(
                        row.index, it.index, -1 * Math.log(it.value.toDouble())
                    )
                )
            }
        }
        bellmanFordSP = BellmanFordSP(edgeWeightedDigraph, 0)
    }

    fun arbitragePossible() = bellmanFordSP.hasNegativeCycle()
    fun arbitragePath(): Iterable<DirectedEdge>? = bellmanFordSP.negativeCycle()?.map {
        DirectedEdge(it.to(), it.from(), "%.3f".format(Math.exp(it.weight())).toDouble())
    }
}