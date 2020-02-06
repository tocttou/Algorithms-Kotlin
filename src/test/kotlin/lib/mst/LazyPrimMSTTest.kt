package lib.mst

import edu.princeton.cs.algs4.In
import lib.utils.ResourceUtils.resolve
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object LazyPrimMSTTest : Spek({
    describe("a lazy prim MST implementation and a weighted undirected graph") {
        val filename = resolve("/mst/undirectedgraph1.txt")
        val `in` = In(filename)
        val edgeWeightedgraph = EdgeWeightedGraph(`in`)
        `in`.close()
        val lazyPrimMST = LazyPrimMST(edgeWeightedgraph)
        context("enumerating the MST's edges") {
            val mstEdges = lazyPrimMST.edges()
            it("should be equal to the test value") {
                val testVal = listOf(
                    Edge(0, 7, 0.16),
                    Edge(1, 7, 0.19),
                    Edge(0, 2, 0.26),
                    Edge(2, 3, 0.17),
                    Edge(5, 7, 0.28),
                    Edge(4, 5, 0.35),
                    Edge(6, 2, 0.4)
                )
                assertEquals(testVal, mstEdges.toList())
            }
        }
        context("calculating the total weight") {
            val weight = lazyPrimMST.weight()
            it("should be equal to the test value") {
                assertEquals(1.81, weight)
            }
        }
    }
})