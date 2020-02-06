package solutions.seam

import edu.princeton.cs.algs4.Picture
import lib.shortestpath.DirectedEdge
import lib.shortestpath.EdgeWeightedDigraph
import kotlin.math.sqrt


class SeamCarver(picture: Picture) {
    private var pictureCopy = Picture(picture.width(), picture.height())

    init {
        for (i in 0..(picture.width() - 1)) {
            for (j in 0..(picture.height() - 1)) {
                pictureCopy.setRGB(i, j, picture.getRGB(i, j))
            }
        }
    }

    fun picture(): Picture {
        val newPicture = Picture(pictureCopy.width(), pictureCopy.height())
        for (i in 0 until pictureCopy.width()) {
            for (j in 0 until pictureCopy.height()) {
                newPicture.setRGB(i, j, pictureCopy.getRGB(i, j))
            }
        }
        return newPicture
    }

    fun width() = pictureCopy.width()
    fun height() = pictureCopy.height()
    fun energy(x: Int, y: Int): Double {
        if ((x < 0 || x > width() - 1) || (y < 0) || y > height() - 1) throw IllegalArgumentException(
            "invalid coordinate range"
        )
        if ((x == 0 || x == width() - 1) || (y == 0 || y == height() - 1)) return 1000.0
        fun square(value: Int) = value * value
        val lp = pictureCopy.get(x - 1, y)
        val rp = pictureCopy.get(x + 1, y)
        val up = pictureCopy.get(x, y + 1)
        val dp = pictureCopy.get(x, y - 1)
        val xColorDiff = arrayOf(lp.red - rp.red, lp.green - rp.green, lp.blue - rp.blue)
        val yColorDiff = arrayOf(up.red - dp.red, up.green - dp.green, up.blue - dp.blue)
        val xGradient = xColorDiff.fold(0) { acc, i -> acc + square(i) }.toDouble()
        val yGradient = yColorDiff.fold(0) { acc, i -> acc + square(i) }.toDouble()
        return sqrt(xGradient + yGradient)
    }

    fun removeHorizontalSeam(seam: Array<Int>) {
        if (height() <= 1) throw IllegalArgumentException("less than 2 horizontal seam in the picture")
        if (seam.size != width()) throw IllegalArgumentException("seam.length must be equal to the width()")
        for (pixelHeight in seam.withIndex()) {
            if (pixelHeight.value < 0 || pixelHeight.value > height() - 1) throw IllegalArgumentException(
                "all pixels must be within range"
            )
            if (pixelHeight.index != seam.size - 1) {
                if (Math.abs(pixelHeight.value - seam[pixelHeight.index + 1]) > 1) throw IllegalArgumentException(
                    "consecutive pixels must not differ by more than 1 height"
                )
            }
        }
        val newPicture = Picture(width(), height() - 1)
        for (i in 0..(pictureCopy.width() - 1)) {
            val seamHeight = seam[i]
            (0..(pictureCopy.height() - 1)).asSequence().filter { it != seamHeight }
                .forEachIndexed { counter, j ->
                    newPicture.setRGB(
                        i, counter, pictureCopy.getRGB(i, j)
                    )
                }
        }
        pictureCopy = newPicture
    }

    fun removeVerticalSeam(seam: Array<Int>) {
        if (width() <= 1) throw IllegalArgumentException("less than 2 vertical seam in the picture")
        if (seam.size != height()) throw IllegalArgumentException("seam.length must be equal to the height()")
        for (pixelWidth in seam.withIndex()) {
            if (pixelWidth.value < 0 || pixelWidth.value > width() - 1) throw IllegalArgumentException(
                "all pixels must be within range"
            )
            if (pixelWidth.index != seam.size - 1) {
                if (Math.abs(pixelWidth.value - seam[pixelWidth.index + 1]) > 1) throw IllegalArgumentException(
                    "consecutive pixels must not differ by more than 1 width"
                )
            }
        }
        val newPicture = Picture(width() - 1, height())
        for (i in 0..(pictureCopy.height() - 1)) {
            val seamWidth = seam[i]
            (0..(pictureCopy.width() - 1)).asSequence().filter { it != seamWidth }
                .forEachIndexed { counter, j ->
                    newPicture.setRGB(
                        counter, i, pictureCopy.getRGB(j, i)
                    )
                }
        }
        pictureCopy = newPicture
    }

    fun findVerticalSeam(): Array<Int> {
        val numJobs = width() * height() + 2
        val distTo = Array(numJobs) { Double.POSITIVE_INFINITY }
        distTo[0] = 0.0
        val edgeTo = Array<Int?>(numJobs) { null }
        val ewd = EdgeWeightedDigraph(numJobs)
        fun pixelToIndex(x: Int, y: Int) = y * width() + x + 1
        fun indexToPixel(index: Int): Pair<Int, Int> {
            val x = (index - 1) % width()
            val y = (index - 1) / width()
            return if (width() > 1) Pair(x, y) else Pair(y, x)
        }

        for (x in 0..(width() - 1)) {
            ewd.addEdge(DirectedEdge(0, pixelToIndex(x, 0), 0.0))
            ewd.addEdge(
                DirectedEdge(
                    pixelToIndex(x, height() - 1), numJobs - 1, energy(x, height() - 1)
                )
            )
            for (y in 0..(height() - 2)) {
                val energy = energy(x, y)
                when (x) {
                    0 -> {
                        if (width() > 1 && height() > 1) {
                            ewd.addEdge(
                                DirectedEdge(pixelToIndex(x, y), pixelToIndex(x + 1, y + 1), energy)
                            )
                        }
                    }
                    width() - 1 -> {
                        if (width() > 1 && height() > 1) {
                            ewd.addEdge(
                                DirectedEdge(pixelToIndex(x, y), pixelToIndex(x - 1, y + 1), energy)
                            )
                        }
                    }
                    else -> {
                        ewd.addEdge(
                            DirectedEdge(pixelToIndex(x, y), pixelToIndex(x - 1, y + 1), energy)
                        )
                        ewd.addEdge(
                            DirectedEdge(pixelToIndex(x, y), pixelToIndex(x + 1, y + 1), energy)
                        )
                    }
                }
                if (height() > 1) {
                    ewd.addEdge(
                        DirectedEdge(pixelToIndex(x, y), pixelToIndex(x, y + 1), energy)
                    )
                }
            }
        }
        for (node in 0..(numJobs - 2)) for (edge in ewd.adj(node)) relax(edge, distTo, edgeTo)
        return pathTo(numJobs - 1, edgeTo, ::indexToPixel, false)
    }

    fun findHorizontalSeam(): Array<Int> {
        val numJobs = width() * height() + 2
        val distTo = Array(numJobs) { Double.POSITIVE_INFINITY }
        distTo[0] = 0.0
        val edgeTo = Array<Int?>(numJobs) { null }
        val ewd = EdgeWeightedDigraph(numJobs)
        fun pixelToIndex(x: Int, y: Int) = x * height() + y + 1
        fun indexToPixel(index: Int): Pair<Int, Int> {
            val y = (index - 1) % height()
            val x = (index - 1) / height()
            return if (height() > 1) Pair(x, y) else Pair(y, x)
        }
        for (y in 0..(height() - 1)) {
            ewd.addEdge(
                DirectedEdge(0, pixelToIndex(0, y), 0.0)
            )
            ewd.addEdge(
                DirectedEdge(
                    pixelToIndex(width() - 1, y), numJobs - 1, energy(width() - 1, y)
                )
            )
            for (x in 0..(width() - 2)) {
                val energy = energy(x, y)
                when (y) {
                    0 -> {
                        if (width() > 1 && height() > 1) {
                            ewd.addEdge(
                                DirectedEdge(pixelToIndex(x, y), pixelToIndex(x + 1, y + 1), energy)
                            )
                        }
                    }
                    height() - 1 -> {
                        if (width() > 1 && height() > 1) {
                            ewd.addEdge(
                                DirectedEdge(pixelToIndex(x, y), pixelToIndex(x + 1, y - 1), energy)
                            )
                        }
                    }
                    else -> {
                        ewd.addEdge(
                            DirectedEdge(pixelToIndex(x, y), pixelToIndex(x + 1, y + 1), energy)
                        )
                        ewd.addEdge(
                            DirectedEdge(pixelToIndex(x, y), pixelToIndex(x + 1, y - 1), energy)
                        )
                    }
                }
                if (width() > 1) {
                    ewd.addEdge(
                        DirectedEdge(pixelToIndex(x, y), pixelToIndex(x + 1, y), energy)
                    )
                }
            }
        }
        for (node in 0..(numJobs - 2)) for (edge in ewd.adj(node)) relax(edge, distTo, edgeTo)
        return pathTo(numJobs - 1, edgeTo, ::indexToPixel, true)
    }

    private fun relax(
        directedEdge: DirectedEdge, distTo: Array<Double>, edgeTo: Array<Int?>
    ) {
        val v = directedEdge.from()
        val w = directedEdge.to()
        if (compareValues(distTo[v] + directedEdge.weight(), distTo[w]) < 0) {
            distTo[w] = distTo[v] + directedEdge.weight()
            edgeTo[w] = directedEdge.from()
        }
    }

    private fun pathTo(
        v: Int, edgeTo: Array<Int?>, indexToPixel: (Int) -> Pair<Int, Int>, horizontalSeam: Boolean
    ): Array<Int> {
        val path = if (horizontalSeam) Array(width()) { -1 } else Array(height()) { -1 }
        var currentEdgeFrom = edgeTo[v]
        var counter = path.size
        while (counter != 0 && currentEdgeFrom != null) {
            val (x, y) = indexToPixel(currentEdgeFrom)
            path[--counter] = if (horizontalSeam) y else x
            currentEdgeFrom = edgeTo[currentEdgeFrom]
        }
        return path
    }
}
