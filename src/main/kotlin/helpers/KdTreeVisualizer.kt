package helpers

import edu.princeton.cs.algs4.In
import edu.princeton.cs.algs4.Point2D
import edu.princeton.cs.algs4.StdDraw
import solutions.kdtree.KdTree

class KdTreeVisualizer {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val filename = args[0]
            val `in` = In(filename)
            val kdTree = KdTree()
            while (!`in`.isEmpty) {
                val x = `in`.readDouble()
                val y = `in`.readDouble()
                val p = Point2D(x, y)
                kdTree.insert(p)
            }

            StdDraw.clear()
            kdTree.draw()
            StdDraw.show()
        }
    }
}