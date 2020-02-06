package lib.directedgraphs

import edu.princeton.cs.algs4.In
import lib.utils.ResourceUtils.resolve
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object MultiSourceShortestPathBFPTest : Spek({
    describe("a MultiSourceShortestPathBFP(G, [1, 7, 10]) on a directed graph") {
        val filename = resolve("/directedgraphs/fullDG.txt")
        val `in` = In(filename)
        val graph = UnweightedDigraph(`in`)
        val breadthFirstPaths = MultiSourceShortestPathBFP(graph, arrayOf(1, 7, 10))
        context("running pathTo(4) on it") {
            it("should give out the vertices in shortest path from [1, 7, 10] to 4") {
                assertEquals(
                    listOf(
                        7, 6, 4
                    ), breadthFirstPaths.pathTo(4)!!.toList()
                )
            }
        }
        context("running pathTo(5) on it") {
            it("should give out the vertices in shortest path from [1, 7, 10] to 5") {
                assertEquals(
                    listOf(
                        7, 6, 0, 5
                    ), breadthFirstPaths.pathTo(5)!!.toList()
                )
            }
        }
    }
})