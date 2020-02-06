package lib.maxflowmincut

import edu.princeton.cs.algs4.In
import lib.utils.ResourceUtils.resolve
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object FlowNetworkTest : Spek({
    describe("a flow network") {
        val filename = resolve("/maxflowmincut/tinyFN.txt")
        val `in` = In(filename)
        val flowNetwork = FlowNetwork(`in`)
        `in`.close()
        context("enumerating its edges") {
            val edges = flowNetwork.edges()
            it("should be equal to the test value") {
                val testVal = listOf(
                    FlowEdge(0, 1, 2.0),
                    FlowEdge(0, 2, 3.0),
                    FlowEdge(1, 3, 3.0),
                    FlowEdge(1, 4, 1.0),
                    FlowEdge(2, 3, 1.0),
                    FlowEdge(2, 4, 1.0),
                    FlowEdge(3, 5, 2.0),
                    FlowEdge(4, 5, 3.0)
                )
                assertEquals(testVal, edges.toList())
                assertEquals(testVal.size, flowNetwork.E())
            }
        }
    }
})