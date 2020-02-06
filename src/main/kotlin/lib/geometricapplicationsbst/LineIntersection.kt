package src.geometricapplicationsbst

import lib.balancedsearchtrees.RedBlackBST
import lib.priorityqueue.MinPQ

/*
No overlapping lines.
All distinct coordinates.
hs format: arrayOf(Pair(left, y), Pair(right, y)).
vs format: arrayOf((Pair(x, up), Pair(x, down)).
 */
class LineIntersection(
    hs: Array<Array<Pair<Int, Int>>>, vs: Array<Array<Pair<Int, Int>>>
) {
    private val scanPoints = MinPQ<ScanPoint>()

    private sealed class ScanPoint : Comparable<ScanPoint> {
        data class Vertical(val x: Int, val lo: Int, val hi: Int) : ScanPoint() {
            override fun compareTo(other: ScanPoint): Int {
                return when (other) {
                    is Vertical -> this.x - other.x
                    is Horizontal -> {
                        when {
                            this.x < other.x -> -1
                            this.x > other.x -> 1
                            else -> -1
                        }
                    }
                }
            }
        }

        data class Horizontal(val x: Int, val y: Int) : ScanPoint() {
            override fun compareTo(other: ScanPoint): Int {
                return when (other) {
                    is Vertical -> this.x - other.x
                    is Horizontal -> this.x - other.x
                }
            }
        }
    }

    init {
        hs.flatten().map { scanPoints.insert(ScanPoint.Horizontal(it.first, it.second)) }
        vs.map { scanPoints.insert(ScanPoint.Vertical(it[0].first, it[1].second, it[0].second)) }
    }

    fun intersect(): Int {
        val redBlackBST = RedBlackBST<Int, Int>()
        var counter = 0
        while (scanPoints.size > 0) {
            val scanPoint = scanPoints.delMin()
            when (scanPoint) {
                is ScanPoint.Horizontal -> {
                    if (!redBlackBST.contains(scanPoint.y)) redBlackBST.put(
                        scanPoint.y, scanPoint.x
                    )
                    else redBlackBST.delete(scanPoint.y)
                }
                is ScanPoint.Vertical -> {
                    counter += redBlackBST.size(scanPoint.lo, scanPoint.hi)
                }
            }
        }
        return counter
    }
}