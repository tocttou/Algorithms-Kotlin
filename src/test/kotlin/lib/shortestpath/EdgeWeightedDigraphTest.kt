package lib.shortestpath

import edu.princeton.cs.algs4.In
import lib.utils.ResourceUtils.resolve
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object EdgeWeightedDigraphTest : Spek({
    describe("an EdgeWeightedDigraph implementation") {
        val filename = resolve("/shortestpath/weighteddigraph1.txt")
        val `in` = In(filename)
        val edgeWeightedDigraph = EdgeWeightedDigraph(`in`)
        `in`.close()
        context("enumerating its edges") {
            val edges = edgeWeightedDigraph.edges()
            it("should be equal to the test value") {
                val testVal = listOf(
                    DirectedEdge(0, 1, 5.0),
                    DirectedEdge(0, 4, 9.0),
                    DirectedEdge(0, 7, 8.0),
                    DirectedEdge(1, 2, 12.0),
                    DirectedEdge(1, 3, 15.0),
                    DirectedEdge(1, 7, 4.0),
                    DirectedEdge(2, 3, 3.0),
                    DirectedEdge(2, 6, 11.0),
                    DirectedEdge(3, 6, 9.0),
                    DirectedEdge(4, 5, 4.0),
                    DirectedEdge(4, 6, 20.0),
                    DirectedEdge(4, 7, 5.0),
                    DirectedEdge(5, 2, 1.0),
                    DirectedEdge(5, 6, 13.0),
                    DirectedEdge(7, 5, 6.0),
                    DirectedEdge(7, 2, 7.0)
                )
                assertEquals(testVal, edges.toList())
            }
        }
    }
})