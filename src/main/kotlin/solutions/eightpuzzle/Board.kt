package solutions.eightpuzzle

import lib.stackqueue.StackLinkedList
import java.util.*

class Board(blocks: Array<Array<Int>>) {
    private val matrix = Array(blocks.size) { blocks[it].clone() }
    private var zeroIndices = Pair(-1, -1)
    private var manhattan = -1

    init {
        for (i in 0 until matrix.size) {
            for (j in 0 until matrix.size) {
                if (matrix[i][j] == 0) {
                    zeroIndices = Pair(i + 1, j + 1)
                    break
                }
            }
        }
        manhattan = manhattan()
    }

    fun dimension() = matrix.size

    fun hamming(): Int {
        var counter = 0
        for (i in 1..(matrix.size)) {
            for (j in 1..(matrix.size)) {
                val currentItem = matrix[i - 1][j - 1]
                if (currentItem != 0 && currentItem != serialize(i, j)) counter++
            }
        }
        return counter
    }

    fun manhattan(): Int {
        if (manhattan != -1) return manhattan
        var counter = 0
        for (i in 1..(matrix.size)) {
            for (j in 1..(matrix.size)) {
                val currentItem = matrix[i - 1][j - 1]
                if (currentItem != 0) {
                    val pos = deserialize(currentItem)
                    counter += Math.abs(pos.first - i) + Math.abs(pos.second - j)
                }
            }
        }
        return counter
    }

    fun isGoal() = manhattan == 0

    fun twin(): Board {
        val firstLocation = fetchNonZeroLocation(1)
        val secondLocation = fetchNonZeroLocation(firstLocation + 1)
        val twinMatrix = Array(matrix.size) { matrix[it].clone() }
        swapLocations(firstLocation, secondLocation, twinMatrix)
        return Board(twinMatrix)
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Board) return false
        if (other.matrix.size != matrix.size) return false
        else {
            if (other.matrix.isNotEmpty() && other.matrix[0].size != matrix[0].size) return false
        }
        for (i in 0 until matrix.size) {
            for (j in 0 until matrix.size) {
                if (matrix[i][j] != other.matrix[i][j]) return false
            }
        }
        return true
    }

    fun neighbors(): Iterable<Board> {
        val (i, j) = zeroIndices
        val flags = Array(4) { 1 }
        val neighbors = StackLinkedList<Board>()
        when (i) {
            1 -> {
                flags[1] = 0
            }
            matrix.size -> {
                flags[3] = 0
            }
        }
        when (j) {
            1 -> {
                flags[0] = 0
            }
            matrix.size -> {
                flags[2] = 0
            }
        }

        for (k in 0..3) {
            if (flags[k] != 0) {
                val zeroLocation = serialize(i, j)
                val neighborMatrix = Array(matrix.size) { matrix[it].clone() }
                val locationToSwap = when (k) {
                    3 -> zeroLocation + matrix.size
                    2 -> zeroLocation + 1
                    1 -> zeroLocation - matrix.size
                    else -> zeroLocation - 1
                }
                swapLocations(zeroLocation, locationToSwap, neighborMatrix)
                neighbors.push(Board(neighborMatrix))
            }
        }
        return neighbors
    }

    override fun toString(): String {
        var build = "${matrix.size}\n"
        for (row in matrix) {
            for (item in row) build += " " + item.toString() + " "
            build += "\n"
        }
        return build
    }

    private fun swapLocations(
        firstLocation: Int, secondLocation: Int, twinMatrix: Array<Array<Int>>
    ) {
        val firstIndex = deserialize(firstLocation)
        val secondIndex = deserialize(secondLocation)
        val temp = twinMatrix[firstIndex.first - 1][firstIndex.second - 1]
        twinMatrix[firstIndex.first - 1][firstIndex.second - 1] =
                twinMatrix[secondIndex.first - 1][secondIndex.second - 1]
        twinMatrix[secondIndex.first - 1][secondIndex.second - 1] = temp
    }

    private fun fetchNonZeroLocation(counter: Int): Int {
        val (i, j) = deserialize(counter)
        return if (matrix[i - 1][j - 1] != 0) counter else counter + 1
    }

    private fun deserialize(location: Int): Pair<Int, Int> {
        val j = if (location % matrix.size != 0) location % matrix.size else matrix.size
        val i = (location - j) / matrix.size + 1
        return Pair(i, j)
    }

    private fun serialize(i: Int, j: Int) = (i - 1) * matrix.size + j

    override fun hashCode() = Arrays.hashCode(matrix)
}