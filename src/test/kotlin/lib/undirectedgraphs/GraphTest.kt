package lib.undirectedgraphs

import edu.princeton.cs.algs4.In
import lib.utils.ResourceUtils.resolve
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object GraphTest : Spek({
    describe("an undirected graph") {
        val filename = resolve("/undirectedgraphs/input1.txt")
        val `in` = In(filename)
        val graph = Graph(`in`)
        context("running V() on it") {
            it("should give out number of vertices") {
                assertEquals(13, graph.V())
            }
        }
        context("running E() on it") {
            it("should give out number of edges") {
                assertEquals(13, graph.E())
            }
        }
    }
})