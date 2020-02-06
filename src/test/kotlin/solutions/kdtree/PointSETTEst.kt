package solutions.kdtree

import edu.princeton.cs.algs4.In
import edu.princeton.cs.algs4.Point2D
import edu.princeton.cs.algs4.RectHV
import lib.utils.ResourceUtils.resolve
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object PointSETTEst : Spek({
    describe("a PointSET implementation") {
        val pointSet = PointSET()
        val filename = resolve("/kdtree/inputRangeErr.txt")
        val `in` = In(filename)
        while (!`in`.isEmpty) {
            val x = `in`.readDouble()
            val y = `in`.readDouble()
            val p = Point2D(x, y)
            pointSet.insert(p)
        }
        context("performing isEmpty, size, range, nearest on it") {
            val isEmpty = pointSet.isEmpty()
            val size = pointSet.size()
            val range = pointSet.range(
                RectHV(0.0, 0.5, 0.125, 0.875)
            ).toList()
            val nearest = pointSet.nearest(Point2D(0.5, 0.5))
            it("should be equal to the test value") {
                assertEquals(isEmpty, false)
                assertEquals(size, 20)
                assertEquals(
                    range, listOf(
                        Point2D(0.0, 0.625),
                        Point2D(0.125, 0.625),
                        Point2D(0.0, 0.75),
                        Point2D(0.125, 0.75)
                    )
                )
                assertEquals(nearest, Point2D(0.375, 0.5))
            }
        }
    }
})