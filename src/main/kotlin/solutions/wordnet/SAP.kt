package solutions.wordnet

import edu.princeton.cs.algs4.Digraph
import edu.princeton.cs.algs4.Queue

class SAP(private val digraph: Digraph) {
    private val V = digraph.V()
    private fun color(
        digraph: Digraph,
        marked: Array<String>,
        distance: Array<Int>,
        color: String,
        opposingColor: String,
        initialNodes: Iterable<Int>
    ) {
        val queue = Queue<Int>()
        val level = Array(V) { -1 }
        for (node in initialNodes) {
            level[node] = 0
            if (marked[node] == "white") {
                marked[node] = color
                queue.enqueue(node)
            } else if (marked[node] == opposingColor) {
                marked[node] = "green"
                queue.enqueue(node)
            }
            distance[node] = level[node]
        }
        while (!queue.isEmpty) {
            val node = queue.dequeue()
            for (adj in digraph.adj(node)) {
                if (marked[adj] == "white") {
                    marked[adj] = color
                    queue.enqueue(adj)
                } else if (marked[adj] == opposingColor) {
                    marked[adj] = "green"
                    queue.enqueue(adj)
                }
                level[adj] = if (level[adj] != -1) {
                    minOf(level[adj], level[node] + 1)
                } else level[node] + 1
                distance[adj] = level[adj]
            }
        }
    }

    fun ancestor(v: Int, w: Int) = ancestor(listOf(v), listOf(w))

    fun ancestor(v: Iterable<Int>, w: Iterable<Int>): Int {
        val distanceRed = Array(V) { -1 }
        val distanceBlue = Array(V) { -1 }
        val marked = processMarked(v, w, distanceRed, distanceBlue)
        return ancestor(marked, distanceRed, distanceBlue).first
    }

    fun length(v: Int, w: Int) = length(listOf(v), listOf(w))

    fun length(v: Iterable<Int>, w: Iterable<Int>): Int {
        val distanceRed = Array(V) { -1 }
        val distanceBlue = Array(V) { -1 }
        val marked = processMarked(v, w, distanceRed, distanceBlue)
        val (ancestor, lLength, rLength) = ancestor(marked, distanceRed, distanceBlue)
        if (ancestor == -1) return -1
        return lLength + rLength
    }

    private fun processMarked(
        v: Iterable<Int>, w: Iterable<Int>, distanceRed: Array<Int>, distanceBlue: Array<Int>
    ): Array<String> {
        v.asSequence().filter { it < 0 || it > V - 1 }.forEach {
            throw IllegalArgumentException("clusters nodes must be between 0 and V")
        }
        w.asSequence().filter { it < 0 || it > V - 1 }.forEach {
            throw IllegalArgumentException("clusters nodes must be between 0 and V")
        }
        val marked = Array(V) { "white" }
        color(digraph, marked, distanceRed, "red", "blue", v)
        color(digraph, marked, distanceBlue, "blue", "red", w)
        return marked
    }

    private fun ancestor(
        marked: Array<String>, distanceRed: Array<Int>, distanceBlue: Array<Int>
    ): Triple<Int, Int, Int> {
        var nearestAncestorIndexSoFar = -1
        var nearestAncestorDistanceSoFar = Int.MAX_VALUE
        for (status in marked.withIndex()) {
            if (status.value == "green") {
                val distance = distanceRed[status.index] + distanceBlue[status.index]
                if (distance < nearestAncestorDistanceSoFar) {
                    nearestAncestorDistanceSoFar = distance
                    nearestAncestorIndexSoFar = status.index
                }
            }
        }
        return Triple(
            nearestAncestorIndexSoFar,
            if (nearestAncestorIndexSoFar > -1) distanceRed[nearestAncestorIndexSoFar] else 0,
            if (nearestAncestorIndexSoFar > -1) distanceBlue[nearestAncestorIndexSoFar] else 0
        )
    }
}