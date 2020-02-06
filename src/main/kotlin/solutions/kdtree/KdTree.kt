package solutions.kdtree

import edu.princeton.cs.algs4.Point2D
import edu.princeton.cs.algs4.RectHV
import edu.princeton.cs.algs4.StdDraw
import lib.stackqueue.QueueResizingArray
import java.util.*

class KdTree {
    private data class Node(
        val key: Point2D,
        var value: String,
        var left: Node?,
        var right: Node?,
        var count: Int,
        val bounds: Array<Double>
    )

    private var root: Node? = null

    fun isEmpty(): Boolean = root == null

    fun size(): Int = size(root)

    private fun size(node: Node?): Int = node?.count ?: 0

    fun contains(p: Point2D): Boolean = contains(root, p) != null

    private fun contains(node: Node?, p: Point2D): Node? {
        if (node == null) return null
        return when {
            p == node.key -> node
            else -> {
                when (node.value) {
                    "vsplit" -> {
                        if (p.x() <= node.key.x()) contains(node.left, p) else contains(
                            node.right, p
                        )
                    }
                    else -> {
                        if (p.y() <= node.key.y()) contains(node.left, p) else contains(
                            node.right, p
                        )
                    }
                }
            }
        }
    }

    fun insert(p: Point2D) {
        root = insert(root, p, "vsplit", arrayOf(0.0, 0.0, 1.0, 1.0))
    }

    private fun insert(
        node: Node?, key: Point2D, value: String, parentBounds: Array<Double>
    ): Node {
        if (node == null) return Node(
            key, value, null, null, 1, parentBounds
        )
        val splitType = node.value
        val bounds = parentBounds.copyOf()
        if (key != node.key) {
            when (splitType) {
                "vsplit" -> {
                    when {
                        key.x() <= node.key.x() -> {
                            bounds[2] = node.key.x()
                            node.left = insert(node.left, key, "hsplit", bounds)
                        }
                        key.x() > node.key.x() -> {
                            bounds[0] = node.key.x()
                            node.right = insert(node.right, key, "hsplit", bounds)
                        }
                    }
                }
                "hsplit" -> {
                    when {
                        key.y() <= node.key.y() -> {
                            bounds[3] = node.key.y()
                            node.left = insert(node.left, key, "vsplit", bounds)
                        }
                        key.y() > node.key.y() -> {
                            bounds[1] = node.key.y()
                            node.right = insert(node.right, key, "vsplit", bounds)
                        }
                    }
                }
            }
        }
        node.count = 1 + size(node.left) + size(node.right)
        return node
    }

    fun draw() = draw(root, null, null)

    private fun draw(node: Node?, parentCoord: Point2D?, parentSplitType: String?) {
        if (node == null) return

        draw(node.left, node.key, node.value)

        StdDraw.setPenColor(StdDraw.BLACK)
        StdDraw.setPenRadius(0.01)
        node.key.draw()
        val penColor = when (parentSplitType) {
            null, "hsplit" -> StdDraw.RED
            else -> StdDraw.BLUE
        }
        StdDraw.setPenColor(penColor)
        StdDraw.setPenRadius()
        if (parentCoord == null) {
            StdDraw.line(node.key.x(), node.bounds[1], node.key.x(), node.bounds[3])
        } else {
            when (parentSplitType) {
                "vsplit" -> {
                    if (node.key.x() <= parentCoord.x()) {
                        StdDraw.line(
                            node.bounds[0], node.key.y(), parentCoord.x(), node.key.y()
                        )
                    } else {
                        StdDraw.line(
                            parentCoord.x(), node.key.y(), node.bounds[2], node.key.y()
                        )
                    }
                }
                "hsplit" -> {
                    if (node.key.y() <= parentCoord.y()) {
                        StdDraw.line(
                            node.key.x(), node.bounds[1], node.key.x(), parentCoord.y()
                        )
                    } else {
                        StdDraw.line(
                            node.key.x(), parentCoord.y(), node.key.x(), node.bounds[3]
                        )
                    }
                }
            }
        }

        draw(node.right, node.key, node.value)
    }

    fun range(rect: RectHV): Iterable<Point2D> {
        val queue = QueueResizingArray<Point2D>()
        range(root, rect, queue)
        return queue
    }

    private fun range(node: Node?, rect: RectHV, queue: QueueResizingArray<Point2D>) {
        if (node == null) return
        if (rect.contains(node.key)) {
            range(node.left, rect, queue)
            queue.push(node.key)
            range(node.right, rect, queue)
        } else {
            when (node.value) {
                "vsplit" -> {
                    when {
                        rect.xmax() < node.key.x() -> range(node.left, rect, queue)
                        rect.xmin() > node.key.x() -> range(node.right, rect, queue)
                        else -> {
                            range(node.left, rect, queue)
                            range(node.right, rect, queue)
                        }
                    }
                }
                "hsplit" -> {
                    when {
                        rect.ymax() < node.key.y() -> range(node.left, rect, queue)
                        rect.ymin() > node.key.y() -> range(node.right, rect, queue)
                        else -> {
                            range(node.left, rect, queue)
                            range(node.right, rect, queue)
                        }
                    }
                }
            }
        }
    }

    private class NodeComparator(val p: Point2D) : Comparator<Node> {
        override fun compare(o1: Node, o2: Node): Int {
            val disDiff = p.distanceSquaredTo(o1.key) - p.distanceSquaredTo(o2.key)
            return disDiff.compareTo(0.0)
        }
    }

    fun nearest(p: Point2D): Point2D? {
        val minPQ = PriorityQueue<Node>(1, NodeComparator(p))
        nearest(root, null, p, minPQ)
        return minPQ.peek()?.key
    }

    private fun nearest(node: Node?, parent: Node?, p: Point2D, minPQ: PriorityQueue<Node>) {
        if (node == null) return
        val nodeRects = if (parent == null) {
            Pair(
                RectHV(0.0, 0.0, node.key.x(), 1.0), RectHV(node.key.x(), 0.0, 1.0, 1.0)
            )
        } else {
            when (parent.value) {
                "vsplit" -> {
                    if (node.key.x() <= parent.key.x()) {
                        Pair(
                            RectHV(
                                node.bounds[0], parent.bounds[1], parent.key.x(), node.key.y()
                            ), RectHV(
                                node.bounds[0], node.key.y(), parent.key.x(), parent.bounds[3]
                            )
                        )
                    } else {
                        Pair(
                            RectHV(
                                parent.bounds[0], parent.bounds[1], node.bounds[2], node.key.y()
                            ), RectHV(
                                parent.key.x(), node.key.y(), node.bounds[2], parent.bounds[3]
                            )
                        )
                    }
                }
                else -> {
                    if (node.key.y() <= parent.key.y()) {
                        Pair(
                            RectHV(
                                parent.bounds[0], node.bounds[1], node.bounds[2], parent.key.y()
                            ), RectHV(
                                node.key.x(), node.bounds[1], parent.bounds[2], parent.key.y()
                            )
                        )
                    } else {
                        Pair(
                            RectHV(
                                parent.bounds[0], node.bounds[1], node.key.x(), node.bounds[3]
                            ), RectHV(
                                node.key.x(), node.bounds[1], parent.bounds[2], node.bounds[3]
                            )
                        )
                    }
                }
            }
        }
        var nearestNodeSoFar = minPQ.peek()
        var nearestDistanceSoFar = (nearestNodeSoFar ?: node).key.distanceSquaredTo(p)
        var distanceLeftRectToP = nodeRects.first.distanceSquaredTo(p)
        var distanceRightRectToP = nodeRects.second.distanceSquaredTo(p)
        val distanceCurrentNodeToP = node.key.distanceSquaredTo(p)
        if (distanceCurrentNodeToP <= nearestDistanceSoFar) minPQ.add(node)
        var leftVisited = false
        var rightVisited = false
        if (distanceLeftRectToP < distanceRightRectToP) {
            nearest(node.left, node, p, minPQ)
            leftVisited = true
        } else {
            nearest(node.right, node, p, minPQ)
            rightVisited = true
        }
        nearestNodeSoFar = minPQ.peek()
        nearestDistanceSoFar = (nearestNodeSoFar ?: node).key.distanceSquaredTo(p)
        distanceLeftRectToP = nodeRects.first.distanceSquaredTo(p)
        distanceRightRectToP = nodeRects.second.distanceSquaredTo(p)
        when {
            rightVisited && !leftVisited -> {
                if (distanceLeftRectToP < nearestDistanceSoFar) nearest(node.left, node, p, minPQ)
            }
            leftVisited && !rightVisited -> {
                if (distanceRightRectToP < nearestDistanceSoFar) nearest(node.right, node, p, minPQ)
            }
        }
    }
}