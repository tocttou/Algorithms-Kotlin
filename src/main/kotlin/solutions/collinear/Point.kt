package solutions.collinear

import edu.princeton.cs.algs4.StdDraw

class Point(private val x: Int, private val y: Int) : Comparable<Point> {
    fun draw() {
        StdDraw.point(x.toDouble(), y.toDouble())
    }

    fun drawTo(that: Point) {
        StdDraw.line(
            x.toDouble(), y.toDouble(), that.x.toDouble(), that.y.toDouble()
        )
    }

    fun slopeTo(that: Point): Double {
        val yDiff = that.y - y
        val xDiff = that.x - x
        return when {
            yDiff == 0 && xDiff == 0 -> Double.NEGATIVE_INFINITY
            yDiff == 0 -> +0.0
            xDiff == 0 -> Double.POSITIVE_INFINITY
            else -> yDiff.toDouble() / xDiff.toDouble()
        }
    }

    override fun compareTo(other: Point): Int {
        val yDiff = y - other.y
        val xDiff = x - other.x
        return when {
            yDiff < 0 -> -1
            yDiff == 0 -> when {
                xDiff < 0 -> -1
                xDiff == 0 -> 0
                else -> 1
            }
            else -> 1
        }
    }

    fun slopeOrder(): Comparator<Point> = SlopeComparator()

    inner class SlopeComparator : Comparator<Point> {
        override fun compare(o1: Point, o2: Point): Int {
            val slope1 = slopeTo(o1)
            val slope2 = slopeTo(o2)
            return when {
                slope1 < slope2 -> -1
                slope1 == slope2 -> 0
                else -> 1
            }
        }
    }

    override fun toString(): String {
        return "($x, $y)"
    }
}