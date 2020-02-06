package lib.directedgraphs

import edu.princeton.cs.algs4.In
import lib.utils.ResourceUtils.resolve
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object KosarajuSharirSCCTest : Spek({
    describe("a directed graph with 4 super components") {
        val filename = resolve("/directedgraphs/fullDG.txt")
        val `in` = In(filename)
        val graph = UnweightedDigraph(`in`)
        context("finding the connected components") {
            val connectedComponents = KosarajuSharirSCC(graph)
            it("should give a total of 3 components") {
                assertEquals(5, connectedComponents.count())
            }
            it("should give id=0 for v=1") {
                assertEquals(0, connectedComponents.id(1))
            }
            it("should give id=4 for v=7") {
                assertEquals(4, connectedComponents.id(7))
            }
            it("should give id=2 for v=9") {
                assertEquals(2, connectedComponents.id(9))
            }
            it("should give id=1 for v=5") {
                assertEquals(1, connectedComponents.id(5))
            }
        }
    }
})