package lib.mst

import edu.princeton.cs.algs4.In
import lib.utils.ResourceUtils.resolve
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object EdgeWeightedGraphTest : Spek({
    describe("an undirected edge weighted graph") {
        val filename = resolve("/mst/undirectedgraph1.txt")
        val `in` = In(filename)
        val edgeWeightedgraph = EdgeWeightedGraph(`in`)
        `in`.close()
        context("enumerating its edges") {
            val edges = edgeWeightedgraph.edges()
            it("should be equal to the test value") {
                val testVal = listOf(
                    Edge(0, 7, 0.16),
                    Edge(0, 2, 0.26),
                    Edge(0, 4, 0.38),
                    Edge(6, 0, 0.58),
                    Edge(1, 7, 0.19),
                    Edge(1, 3, 0.29),
                    Edge(1, 5, 0.32),
                    Edge(1, 2, 0.36),
                    Edge(2, 3, 0.17),
                    Edge(2, 7, 0.34),
                    Edge(6, 2, 0.4),
                    Edge(3, 6, 0.52),
                    Edge(4, 5, 0.35),
                    Edge(4, 7, 0.37),
                    Edge(6, 4, 0.93),
                    Edge(5, 7, 0.28)
                )
                assertEquals(testVal, edges.toList())
            }
        }
    }
})