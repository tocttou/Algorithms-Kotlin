package lib.elementarysorts.applications

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object GrahamScanTest : Spek({
    describe("a set of integer 2D coordinates") {
        val coordinates = arrayOf(
            Pair(0, 0),
            Pair(0, 2),
            Pair(1, 2),
            Pair(-1, 1),
            Pair(2, 0),
            Pair(-2, 2),
            Pair(-2, 0),
            Pair(0, -1)
        )
        context("finding their convex hull using Graham scan algorithm") {
            val grahamScan = GrahamScan(coordinates)
            val convexHull = grahamScan.getConvexHull()
            it("should be equal to the test value") {
                val testVal = listOf(
                    Pair(-2, 0), Pair(-2, 2), Pair(1, 2), Pair(2, 0), Pair(0, -1)
                )
                assertEquals(testVal, convexHull)
            }
        }
    }
})
