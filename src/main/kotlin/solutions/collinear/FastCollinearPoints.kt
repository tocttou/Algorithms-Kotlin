package solutions.collinear

import helpers.LineSegment
import lib.mergesort.Merge
import lib.stackqueue.QueueLinkedList
import lib.stackqueue.StackLinkedList

class FastCollinearPoints(points: Array<Point>) {
    private val numberOfSegments: Int
    private var segments: List<LineSegment>

    init {
        val pointsCopy = points.copyOf()
        Merge.sort(pointsCopy)
        for (i in 0..(pointsCopy.size - 2)) {
            if (pointsCopy[i].compareTo(pointsCopy[i + 1]) == 0) throw IllegalArgumentException("each point must be distinct")
        }

        val segmentEndpointsQueue = QueueLinkedList<Array<Point>>()
        for ((i, leadingPoint) in pointsCopy.withIndex()) {
            val pointsCopy2 = Array(pointsCopy.size - 1 - i) {
                pointsCopy[it + i + 1]
            }
            Merge.sort(pointsCopy2, leadingPoint.slopeOrder())
            val tempStack = StackLinkedList<Point>()
            for ((j, indexPoint) in pointsCopy2.withIndex()) {
                when {
                    tempStack.size == 0 -> tempStack.push(indexPoint)
                    leadingPoint.slopeTo(tempStack.peek()) == leadingPoint.slopeTo(indexPoint) -> tempStack.push(
                        indexPoint
                    )
                    else -> {
                        if (tempStack.size >= 3) segmentEndpointsQueue.push(
                            arrayOf(
                                leadingPoint, tempStack.pop()
                            )
                        )
                        while (tempStack.size != 0) tempStack.pop()
                        tempStack.push(indexPoint)
                    }
                }
                if (j == pointsCopy2.size - 1 && tempStack.size >= 3) segmentEndpointsQueue.push(
                    arrayOf(
                        leadingPoint, tempStack.pop()
                    )
                )
            }
        }

        val tempStack = StackLinkedList<Array<Point>>()
        val segmentsEndpoints = arrayOfNulls<Array<Point>>(segmentEndpointsQueue.size)
        for ((index, endPoints) in segmentEndpointsQueue.withIndex()) segmentsEndpoints[index] =
                endPoints
        while (segmentEndpointsQueue.size != 0) {
            val line1 = segmentEndpointsQueue.pop()
            var isMaximal = 1
            for (line2 in segmentsEndpoints) {
                if (line1[0].compareTo(line2!![0]) == 0 && line1[1].compareTo(line2[1]) == 0) continue
                if (!areCoincidentLines(line1, line2)) isMaximal = 1
                else {
                    if (line1[0] <= line2[0] && line1[1] >= line2[1]) isMaximal = 1
                    else {
                        isMaximal = 0
                        break
                    }
                }
            }
            if (isMaximal == 1) tempStack.push(line1)
        }

        segments = tempStack.map { LineSegment(it[0], it[1]) }
        numberOfSegments = segments.size
    }

    private fun areCoincidentLines(line1: Array<Point>, line2: Array<Point>): Boolean {
        val slope1 = line1[0].slopeTo(line1[1])
        val slope2 = line2[0].slopeTo(line2[1])
        if (slope1.compareTo(slope2) != 0) return false
        val slope3 = line1[0].slopeTo(line2[0])
        return slope1.compareTo(slope3) == 0
    }

    fun numberOfSegments(): Int = numberOfSegments

    fun segments() = segments.toTypedArray()
}