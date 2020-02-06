package solutions.collinear

import lib.utils.ResourceUtils.resolve
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.io.File
import kotlin.test.assertEquals

object FastCollinearPointsTest : Spek({
    describe("1. a grid of points") {
        val grid = arrayOf(
            Point(10000, 0),
            Point(5000, 5000),
            Point(0, 10000),
            Point(3000, 7000),
            Point(7000, 3000),
            Point(20000, 21000),
            Point(3000, 4000),
            Point(14000, 15000),
            Point(6000, 7000)
        )
        context("passing them through FastCollinearPoints") {
            val collinear = FastCollinearPoints(grid)
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
        context("passing them through FastCollinearPoints") {
            val collinear = FastCollinearPoints(grid)
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
    describe("3. a grid of points") {
        val inputStream = File(resolve("/collinear/input40.txt")).inputStream()
        val bufferedReader = inputStream.bufferedReader()
        val numberOfPoints = bufferedReader.readLine()!!.trim().toInt()
        val grid = Array(numberOfPoints) {
            val (x, y) = bufferedReader.readLine()!!.trim().split(" ").filterNot { it == "" }.map { it.toInt() }
            Point(x, y)
        }
        context("passing them through FastCollinearPoints") {
            val collinear = FastCollinearPoints(grid)
            it("should give out the line segments") {
                val testVal = listOf(
                    "(2000, 29000) -> (28000, 29000)",
                    "(2000, 24000) -> (25000, 24000)",
                    "(1000, 17000) -> (1000, 31000)",
                    "(1000, 17000) -> (29000, 17000)"
                )
                assertEquals(collinear.numberOfSegments(), 4)
                assertEquals(List(collinear.numberOfSegments()) {
                    collinear.segments()[it].toString()
                }, testVal)
            }
        }
    }
})