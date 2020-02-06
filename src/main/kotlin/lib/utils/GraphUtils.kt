package lib.utils

import lib.undirectedgraphs.Graph

object GraphUtils {
    fun degree(G: Graph, v: Int): Int = G.adj(v).count()

    fun maxDegree(G: Graph): Int {
        var max = 0
        for (vertex in 0..(G.V() - 1)) {
            if (degree(G, vertex) > max) max = degree(G, vertex)
        }
        return max
    }

    fun averageDegree(G: Graph): Double = 2.0 * G.E() / G.V()

    fun numberOfSelfLoops(G: Graph): Int {
        var count = 0
        for (vertex in 0..(G.V() - 1)) {
            for (w in G.adj(vertex)) if (vertex == w) count++
        }
        return count
    }
}