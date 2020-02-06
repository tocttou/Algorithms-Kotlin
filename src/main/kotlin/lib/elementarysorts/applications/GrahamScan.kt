package lib.elementarysorts.applications

import lib.stackqueue.StackResizingArray
import java.util.HashSet
import kotlin.Comparator

/*
 ~n for initialization
 ~nlog(n) for getConvexHull()
 Distinct pairs of coordinates required
*/

class GrahamScan(coordinates: Array<Pair<Int, Int>>) {
    private val points = Array(coordinates.size) {
        Point(coordinates[it].first, coordinates[it].second)
    }
    private val hull = StackResizingArray<Point>()

    init {
        if (hasDuplicate(coordinates)) throw IllegalArgumentException("Distinct points required.")
        if (coordinates.size < 3) throw IllegalArgumentException("At least 3 points required.")
    }

    private fun hasDuplicate(items: Array<Pair<Int, Int>>): Boolean {
        val appeared = HashSet<Pair<Int, Int>>()
        return items.any { !appeared.add(it) }
    }

    fun getConvexHull(): List<Pair<Int, Int>> {
        Points2D.sortByPolarAngle(points)
        hull.push(points[0]); hull.push(points[1])
        for (i in 2..(points.size - 1)) {
            var top = hull.pop()
            while (!hull.isEmpty() && Points2D.ccw(hull.peek(), top, points[i]) <= 0) top =
                    hull.pop()
            hull.push(top)
            hull.push(points[i])
        }
        return hull.map { Pair(it.x, it.y) }
    }
}

data class Point(val x: Int, val y: Int) : Comparable<Point> {
    override fun compareTo(other: Point): Int {
        return when {
            this.y - other.y == 0 -> other.x - this.x
            else -> this.y - other.y
        }
    }
}

object Points2D {
    private fun polarAngle(point: Point, minYPoint: Point): Double {
        val yDiff = point.y.toDouble() - minYPoint.y.toDouble()
        val xDiff = point.x.toDouble() - minYPoint.x.toDouble()
        val slope = yDiff / xDiff
        return when {
            slope > 0 -> Math.atan(slope)
            slope < 0 -> Math.abs(Math.atan(1 / slope)) + Math.PI / 2
            1 / slope == Double.NEGATIVE_INFINITY -> Math.PI
            else -> 0.0
        }
    }

    fun sortByPolarAngle(points: Array<Point>) {
        if (points.size < 3) throw IllegalArgumentException("At least 3 points required.")
        val minYPoint = points.min()!!
        points.sortWith( // inbuilt Tim sort ~nlog(n)
            Comparator { o1, o2 ->
                when {
                    polarAngle(o1, minYPoint) > polarAngle(o2, minYPoint) -> 1
                    polarAngle(o1, minYPoint) < polarAngle(o2, minYPoint) -> -1
                    else -> 0
                }
            })
    }

    fun ccw(a: Point, b: Point, c: Point): Int {
        val area = (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x)
        return when {
            area < 0 -> -1
            area > 0 -> 1
            else -> 0
        }
    }
}