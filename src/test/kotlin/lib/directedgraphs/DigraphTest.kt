package lib.directedgraphs

import edu.princeton.cs.algs4.Digraph
import edu.princeton.cs.algs4.In
import lib.utils.ResourceUtils.resolve
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object DigraphTest : Spek({
    describe("a directed graph") {
        val filename = resolve("/directedgraphs/tinyDG2.txt")
        val `in` = In(filename)
        val graph = Digraph(`in`)
        context("running V() on it") {
            it("should give out number of vertices") {
                assertEquals(6, graph.V())
            }
        }
        context("running E() on it") {
            it("should give out number of edges") {
                assertEquals(8, graph.E())
            }
        }
    }
})
