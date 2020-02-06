package solutions.eightpuzzle

import edu.princeton.cs.algs4.MinPQ
import lib.stackqueue.StackLinkedList

class Solver(initial: Board) {
    private val minPQ = MinPQ(0, ManhattanComparator())
    private val minpqTwin = MinPQ(0, ManhattanComparator())
    private var moves = -1
    private var isSolvable = false
    private var lastSolutionNode: SearchNode? = null
    private val solution: StackLinkedList<Board>?

    private data class SearchNode(
        val board: Board, var numMoves: Int, var lastNode: SearchNode?
    )

    private class HammingComparator : Comparator<SearchNode> {
        override fun compare(o1: SearchNode, o2: SearchNode): Int {
            val hamming1 = o1.board.hamming() + o1.numMoves
            val hamming2 = o2.board.hamming() + o2.numMoves
            return when {
                hamming1 < hamming2 -> -1
                hamming1 == hamming2 -> 0
                else -> 1
            }
        }
    }

    private class ManhattanComparator : Comparator<SearchNode> {
        override fun compare(o1: SearchNode, o2: SearchNode): Int {
            val manhattan1 = o1.board.manhattan() + o1.numMoves
            val manhattan2 = o2.board.manhattan() + o2.numMoves
            return when {
                manhattan1 < manhattan2 -> -1
                manhattan1 == manhattan2 -> 0
                else -> 1
            }
        }
    }

    private fun proceed(pq: MinPQ<SearchNode>, branch: String): Boolean {
        val minNode = pq.delMin()
        if (minNode.board.isGoal()) {
            if (branch == "main") {
                isSolvable = true
                moves = minNode.numMoves
                lastSolutionNode = minNode
            }
            return false
        } else insertNeighbors(minNode, pq)
        return true
    }

    private fun insertNeighbors(currentNode: SearchNode, pq: MinPQ<SearchNode>) {
        for (neighbor in currentNode.board.neighbors()) {
            if (neighbor != currentNode.lastNode?.board) {
                pq.insert(SearchNode(neighbor, currentNode.numMoves + 1, currentNode))
            }
        }
    }

    init {
        if (initial.isGoal()) {
            isSolvable = true
            moves = 0
            lastSolutionNode = SearchNode(initial, 0, null)
        } else {
            var counter = 0
            insertNeighbors(SearchNode(initial, 0, null), minPQ)
            insertNeighbors(SearchNode(initial.twin(), 0, null), minpqTwin)
            findSolution@ while (true) {
                when (counter % 2) {
                    0 -> {
                        if (!proceed(minPQ, "main")) break@findSolution
                        counter++
                    }
                    1 -> {
                        if (!proceed(minpqTwin, "twin")) break@findSolution
                        counter++
                    }
                }
            }
        }
        solution = if (isSolvable) {
            val sols = StackLinkedList<Board>()
            var lastProcessedNode = lastSolutionNode
            while (lastProcessedNode != null) {
                sols.push(lastProcessedNode.board)
                lastProcessedNode = lastProcessedNode.lastNode
            }
            sols
        } else null
    }

    fun moves() = moves
    fun isSolvable() = isSolvable
    fun solution(): Iterable<Board>? = solution
}