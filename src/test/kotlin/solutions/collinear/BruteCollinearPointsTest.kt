package solutions.collinear

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object BruteCollinearPointsTest : Spek({
    describe("1. a grid of points") {
        val grid = arrayOf(
            Point(10000, 0),
            Point(0, 10000),
            Point(3000, 7000),
            Point(7000, 3000),
            Point(20000, 21000),
            Point(3000, 4000),
            Point(14000, 15000),
            Point(6000, 7000)
        )
        context("passing them through BruteCollinearPoints") {
            val collinear = BruteCollinearPoints(grid)
            it("should give out the line segments") {
                val testVal = listOf(
                    "(3000, 4000) -> (20000, 21000)", "(10000, 0) -> (0, 10000)"
                )
                assertEquals(2, collinear.numberOfSegments())
                assertEquals(List(collinear.numberOfSegments()) {
                    collinear.segments()[it].toString()
                }, testVal)
            }
        }
    }
    describe("2. a grid of points") {
        val grid = arrayOf(
            Point(19000, 10000),
            Point(32000, 10000),
            Point(21000, 10000),
            Point(1234, 5678),
            Point(14000, 10000)
        )
        context("passing them through BruteCollinearPoints") {
            val collinear = BruteCollinearPoints(grid)
            it("should give out the line segments") {
                val testVal = listOf(
                    "(14000, 10000) -> (32000, 10000)"
                )
                assertEquals(collinear.numberOfSegments(), 1)
                assertEquals(List(collinear.numberOfSegments()) {
                    collinear.segments()[it].toString()
                }, testVal)
            }
        }
    }
})