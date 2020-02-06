package lib.undirectedgraphs

import edu.princeton.cs.algs4.In
import lib.utils.ResourceUtils.resolve
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object CCTest : Spek({
    describe("an undirected graph with 3 components") {
        val filename = resolve("/undirectedgraphs/input1.txt")
        val `in` = In(filename)
        val graph = Graph(`in`)
        context("finding the connected components") {
            val connectedComponents = CC(graph)
            it("should give a total of 3 components") {
                assertEquals(3, connectedComponents.count())
            }
            it("should give id=2 for v=9") {
                assertEquals(2, connectedComponents.id(9))
            }
            it("should give id=0 for v=5") {
                assertEquals(0, connectedComponents.id(5))
            }
        }
    }
})