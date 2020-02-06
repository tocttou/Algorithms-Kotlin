package lib.directedgraphs

import edu.princeton.cs.algs4.In
import lib.utils.ResourceUtils.resolve
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object TopologicalOrderTest : Spek({
    describe("a directed graph") {
        val filename = resolve("/directedgraphs/fullDG2.txt")
        val `in` = In(filename)
        val graph = UnweightedDigraph(`in`)
        context("finding its topological order") {
            val topologicalOrder = TopologicalOrder(graph)
            it("should give the same reversePost as the test value") {
                assertEquals(
                    listOf(3, 6, 0, 1, 4, 5, 2), topologicalOrder.sort()!!.toList()
                )
            }
        }
    }
    describe("a directed graph containing a cycle") {
        val filename = "/directedgraphs/fullDG.txt"
        val `in` = In(filename)
        val graph = UnweightedDigraph(`in`)
        val topologicalOrder = TopologicalOrder(graph)
        context("checking if it contains a cycle") {
            it("should have hasCycle=true") {
                assertEquals(true, topologicalOrder.hasCycle())
            }
        }
    }
})