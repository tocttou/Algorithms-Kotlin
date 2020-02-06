package lib.shortestpath.applications

import lib.shortestpath.DirectedEdge
import lib.utils.ResourceUtils.resolve
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object ArbitrageDetectionTest : Spek({
    describe("an ArbitrageDetection implementation and a currency exchange matrix") {
        val filename = resolve("/shortestpath/arbitragedetection.txt")
        val arbitrageDetection = ArbitrageDetection(filename)
        context("checking if arbitrage is possible") {
            it("should be true") {
                assertEquals(true, arbitrageDetection.arbitragePossible())
            }
        }
        context("finding one of the arbitrage paths") {
            val testVal = listOf(
                DirectedEdge(3, 2, 0.62), DirectedEdge(2, 3, 1.613)
            )
            it("should be equal to the test value") {
                assertEquals(testVal, arbitrageDetection.arbitragePath()!!.toList())
            }
        }
    }
})