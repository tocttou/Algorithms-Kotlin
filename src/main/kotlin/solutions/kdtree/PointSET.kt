package solutions.kdtree

import edu.princeton.cs.algs4.MinPQ
import edu.princeton.cs.algs4.Point2D
import edu.princeton.cs.algs4.RectHV
import lib.stackqueue.QueueResizingArray

class PointSET {
    private val redBlackBST = sortedSetOf<Point2D>()
    fun isEmpty(): Boolean = redBlackBST.isEmpty()
    fun size(): Int = redBlackBST.size
    fun insert(p: Point2D) = redBlackBST.add(p)
    fun contains(p: Point2D): Boolean = redBlackBST.contains(p)
    fun draw() = redBlackBST.map { it.draw() }
    fun range(rect: RectHV): Iterable<Point2D> {
        val queue = QueueResizingArray<Point2D>()
        redBlackBST.map { if (rect.contains(it)) queue.push(it) }
        return queue
    }

    fun nearest(p: Point2D): Point2D {
        val minPQ = MinPQ<Point2D>(1, p.distanceToOrder())
        redBlackBST.map { minPQ.insert(it) }
        return minPQ.delMin()
    }
}