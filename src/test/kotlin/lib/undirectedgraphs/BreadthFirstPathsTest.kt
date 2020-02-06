package lib.undirectedgraphs

import edu.princeton.cs.algs4.In
import lib.utils.ResourceUtils.resolve
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object BreadthFirstPathsTest : Spek({
    describe("a BreadthFirstPaths(G, 0) on an undirected graph") {
        val filename = resolve("/undirectedgraphs/input1.txt")
        val `in` = In(filename)
        val graph = Graph(`in`)
        val breadthFirstPaths = BreadthFirstPaths(graph, 0)
        context("running pathTo(3) on it") {
            it("should give out the vertices in shortest path from 0 to 3") {
                assertEquals(
                    listOf(
                        0, 5, 3
                    ), breadthFirstPaths.pathTo(3)!!.toList()
                )
            }
        }
        context("running pathTo(5) on it") {
            it("should give out the vertices in shortest path from 0 to 5") {
                assertEquals(
                    listOf(
                        0, 5
                    ), breadthFirstPaths.pathTo(5)!!.toList()
                )
            }
        }
    }
})