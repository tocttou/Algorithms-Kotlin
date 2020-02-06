package lib.shortestpath

import edu.princeton.cs.algs4.In
import lib.utils.ResourceUtils.resolve
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object BellmanFordSPTest : Spek({
    describe("an BellmanFordSP implementation and an edge weighed digraph (no -ve cycle)") {
        val filename = resolve("/shortestpath/weighteddigraph1.txt")
        val `in` = In(filename)
        val edgeWeightedDigraph = EdgeWeightedDigraph(`in`)
        `in`.close()
        val bellmanFordSP = BellmanFordSP(edgeWeightedDigraph, 0)
        context("calculating the shortest path to v=2 from s=0") {
            val shortestPath = bellmanFordSP.pathTo(2)
            it("should be equal to the test value") {
                val testValue = listOf(
                    DirectedEdge(0, 4, 9.0), DirectedEdge(4, 5, 4.0), DirectedEdge(5, 2, 1.0)
                )
                assertEquals(testValue, shortestPath.toList())
            }
        }
        context("calculating the shortest distance to v=2 from s=0") {
            val shortestDistance = bellmanFordSP.distTo(2)
            it("should be equal to the test value") {
                val testVal = 14.0
                assertEquals(testVal, shortestDistance)
            }
        }
    }
    describe("an BellmanFordSP implementation and an edge weighed digraph (with -ve cycle)") {
        val filename = "/shortestpath/weightedcyclegraph1.txt"
        val `in` = In(filename)
        val edgeWeightedDigraph = EdgeWeightedDigraph(`in`)
        `in`.close()
        val bellmanFordSP = BellmanFordSP(edgeWeightedDigraph, 0)
        context("checking if negative cycle exists") {
            val hasNegativeCycle = bellmanFordSP.hasNegativeCycle()
            it("should be equal to true") {
                assertEquals(true, hasNegativeCycle)
            }
        }
        context("finding the negative cycle") {
            val negativeCycle = bellmanFordSP.negativeCycle()!!
            it("should be equal to the test value") {
                val testVal = listOf(
                    DirectedEdge(4, 5, 0.35), DirectedEdge(5, 4, -0.66)
                )
                assertEquals(testVal, negativeCycle.toList())
            }
        }
    }
})