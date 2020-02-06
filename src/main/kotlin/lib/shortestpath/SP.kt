package lib.shortestpath

import edu.princeton.cs.algs4.Stack

abstract class SP {
    protected abstract val distTo: Array<Double>
    protected abstract val edgeTo: Array<DirectedEdge?>
    protected abstract fun relax(directedEdge: DirectedEdge)
    fun distTo(v: Int) = distTo[v]
    fun pathTo(v: Int): Iterable<DirectedEdge> {
        val path = Stack<DirectedEdge>()
        var currentNode = v
        var currentEdge = edgeTo[currentNode]
        while (currentEdge != null) {
            path.push(currentEdge)
            currentNode = currentEdge.from()
            currentEdge = edgeTo[currentNode]
        }
        return path
    }

    fun hasPathTo(v: Int) = edgeTo[v] != null
}

