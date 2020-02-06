package lib.undirectedgraphs

import edu.princeton.cs.algs4.In
import lib.utils.ResourceUtils.resolve
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object DepthFirstPathsTest : Spek({
    describe("a DepthFirstPaths(G, 0) on an undirected graph") {
        val filename = resolve("/undirectedgraphs/input1.txt")
        val `in` = In(filename)
        val graph = Graph(`in`)
        val depthFirstPaths = DepthFirstPaths(graph, 0)
        context("running pathTo(3) on it") {
            it("should give out the vertices in path from 0 to 3") {
                assertEquals(
                    listOf(
                        0, 5, 4, 3
                    ), depthFirstPaths.pathTo(3)!!.toList()
                )
            }
        }
        context("running pathTo(5) on it") {
            it("should give out the vertices in path from 0 to 5") {
                assertEquals(
                    listOf(
                        0, 5
                    ), depthFirstPaths.pathTo(5)!!.toList()
                )
            }
        }
    }
})