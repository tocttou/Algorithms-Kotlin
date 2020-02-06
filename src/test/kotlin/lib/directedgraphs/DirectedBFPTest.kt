package lib.directedgraphs

import edu.princeton.cs.algs4.In
import lib.utils.ResourceUtils.resolve
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object DirectedBFPTest : Spek({
    describe("a DirectedBFP(G, 0) on a directed graph") {
        val filename = resolve("/directedgraphs/tinyDG2.txt")
        val `in` = In(filename)
        val graph = UnweightedDigraph(`in`)
        val breadthFirstPaths = DirectedBFP(graph, 0)
        context("running pathTo(3) on it") {
            it("should give out the vertices in shortest path from 0 to 3") {
                assertEquals(
                    listOf(
                        0, 2, 4, 3
                    ), breadthFirstPaths.pathTo(3)!!.toList()
                )
            }
        }
        context("running pathTo(5) on it") {
            it("should give out the vertices in shortest path from 0 to 5") {
                assertEquals(
                    listOf(
                        0, 2, 4, 3, 5
                    ), breadthFirstPaths.pathTo(5)!!.toList()
                )
            }
        }
    }
})