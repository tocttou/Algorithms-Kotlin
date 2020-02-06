package helpers

import edu.princeton.cs.algs4.In
import edu.princeton.cs.algs4.StdDraw
import edu.princeton.cs.algs4.StdOut
import solutions.collinear.FastCollinearPoints
import solutions.collinear.Point

fun main(args: Array<String>) {
    val inp = In(args[0])
    val n = inp.readInt()
    val points = Array(n) {
        val x = inp.readInt()
        val y = inp.readInt()
        Point(x, y)
    }

    StdDraw.enableDoubleBuffering()
    StdDraw.setXscale(0.0, 32768.0)
    StdDraw.setYscale(0.0, 32768.0)
    for (p in points) p.draw()
    StdDraw.show()

    val collinear = FastCollinearPoints(points)
    for (segment in collinear.segments()) {
        StdOut.println(segment)
        segment.draw()
    }
    StdDraw.show()
}