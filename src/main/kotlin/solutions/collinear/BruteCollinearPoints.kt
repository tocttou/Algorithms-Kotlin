package solutions.collinear

import helpers.LineSegment
import lib.mergesort.Merge
import lib.stackqueue.StackResizingArray

class BruteCollinearPoints(points: Array<Point>) {
    private val numberOfSegments: Int
    private var segments: Array<LineSegment>

    init {
        val pointsCopy = Array(points.size) { points[it] }
        Merge.sort(pointsCopy)
        for (i in 0..(pointsCopy.size - 2)) {
            if (pointsCopy[i].compareTo(pointsCopy[i + 1]) == 0) throw IllegalArgumentException("each point must be distinct")
        }
        val segmentStack = StackResizingArray<LineSegment>()
        for (i in 0..(pointsCopy.size - 4)) {
            for (j in (i + 3) until pointsCopy.size) {
                val slope1 = pointsCopy[i].slopeTo(pointsCopy[j])
                for (k in (i + 1)..(j - 2)) {
                    val slope2 = pointsCopy[i].slopeTo(pointsCopy[k])
                    for (l in (k + 1) until j) {
                        val slope3 = pointsCopy[i].slopeTo(pointsCopy[l])
                        if (slope1 == slope2 && slope2 == slope3) segmentStack.push(
                            LineSegment(
                                pointsCopy[i], pointsCopy[j]
                            )
                        )
                    }
                }
            }
        }
        numberOfSegments = segmentStack.size
        segments = Array(numberOfSegments()) { segmentStack.pop() }
    }

    fun numberOfSegments(): Int = numberOfSegments

    fun segments() = segments.copyOf()
}