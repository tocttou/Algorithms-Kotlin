package lib.geometricapplicationsbst

import lib.geometricapplicationsbst.Interval1DST.Interval
import lib.priorityqueue.MinPQ

/*
No overlapping lines.
All distinct coordinates.
hr format: arrayOf(Pair(x1, Interval(down, up)), Pair(x2, Interval(down, up))).
vr format: arrayOf(Pair(x1, Interval(down, up)), Pair(x2, Interval(down, up))).
 */

typealias LineSegment = Pair<Int, Interval<Int>>
typealias Rectangle = Array<LineSegment>

class RectangleIntersection(hr: Array<Rectangle>, vr: Array<Rectangle>) {
    private val scanPoints = MinPQ<ScanPoint>()

    private sealed class ScanPoint : Comparable<ScanPoint> {
        data class SegmentVR(val ls: LineSegment) : ScanPoint() {
            override fun compareTo(other: ScanPoint): Int {
                return when (other) {
                    is SegmentVR -> ls.first - other.ls.first
                    is SegmentHR -> {
                        when {
                            ls.first < other.ls.first -> -1
                            ls.first > other.ls.first -> 1
                            else -> -1
                        }
                    }
                }
            }
        }

        data class SegmentHR(val ls: LineSegment) : ScanPoint() {
            override fun compareTo(other: ScanPoint): Int {
                return when (other) {
                    is SegmentVR -> ls.first - other.ls.first
                    is SegmentHR -> ls.first - other.ls.first
                }
            }
        }
    }

    init {
        hr.map { it.forEach { ls -> scanPoints.insert(ScanPoint.SegmentHR(ls)) } }
        vr.map { it.forEach { ls -> scanPoints.insert(ScanPoint.SegmentVR(ls)) } }
    }

    // worst case: N^N
    // worst case if Interval1DST uses a RedBlack tree as a base instead of a BST: NlogN
    fun intersect(): Int {
        val interval1DST = Interval1DST<Int>()
        var counter = 0
        while (scanPoints.size > 0) {
            val scanPoint = scanPoints.delMin()
            when (scanPoint) {
                is ScanPoint.SegmentHR -> {
                    if (!interval1DST.contains(
                            scanPoint.ls.second.lo, scanPoint.ls.second.hi
                        )
                    ) interval1DST.put(
                        scanPoint.ls.second.lo, scanPoint.ls.second.hi
                    )
                    else interval1DST.delete(
                        scanPoint.ls.second.lo, scanPoint.ls.second.hi
                    )
                }
                is ScanPoint.SegmentVR -> {
                    counter += interval1DST.intersectSize(
                        scanPoint.ls.second.lo, scanPoint.ls.second.hi
                    )
                }
            }
        }
        return counter
    }
}