package lib.maxflowmincut

import edu.princeton.cs.algs4.In
import lib.utils.ResourceUtils.resolve
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object FordFulkersonTest: Spek({
    describe("a FordFulkerson implementation for maxflow/mincut and a flow network") {
        val filename = resolve("/maxflowmincut/tinyFN.txt")
        val `in` = In(filename)
        val flowNetwork = FlowNetwork(`in`)
        val fordFulkerson = FordFulkerson(flowNetwork, 0, 5)
        context("calculating the maxflow") {
            val maxflow = fordFulkerson.value()
            it("should be equal to 4.0") {
                val testVal = 4.0
                assertEquals(testVal, maxflow)
            }
        }
    }
})