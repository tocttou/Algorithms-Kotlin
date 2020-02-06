// does not correct for https://www.sigmainfy.com/blog/avoid-backwash-in-percolation.html
// score: 93 / 100
package solutions.percolation

import edu.princeton.cs.algs4.WeightedQuickUnionUF

class Percolation(private val n: Int) {
    private var grid: Array<IntArray>
    private var uf: WeightedQuickUnionUF
    private var numOpenSites = 0

    init {
        if (n <= 0) throw IllegalArgumentException("n must be greater than 0")
        grid = Array(n) { IntArray(n) { 0 } }
        uf = WeightedQuickUnionUF(n * n + 2)
    }

    private fun linearized(row: Int, col: Int): Int {
        validateInput(row, col)
        return (row - 1) * n + col
    }

    private fun connectIfOpenInCol(row: Int, col: Int) {
        if (col > 1 && isOpen(row, col - 1)) {
            uf.union(
                linearized(row, col), linearized(row, col - 1)
            )
        }
        if (col < n && isOpen(row, col + 1)) {
            uf.union(
                linearized(row, col), linearized(row, col + 1)
            )
        }
    }

    private fun connectIfOpenInUpperRow(row: Int, col: Int) {
        if (isOpen(row - 1, col)) {
            uf.union(
                linearized(row, col), linearized(row - 1, col)
            )
        }
    }

    private fun connectIfOpenInBelowRow(row: Int, col: Int) {
        if (isOpen(row + 1, col)) {
            uf.union(
                linearized(row, col), linearized(row + 1, col)
            )
        }
    }

    private fun validateInput(row: Int, col: Int) {
        if (row < 1 || row > n) throw IllegalArgumentException("row must be between 1 and $n")
        if (col < 1 || col > n) throw IllegalArgumentException("col must be between 1 and $n")
    }

    fun open(row: Int, col: Int) {
        validateInput(row, col)
        if (!isOpen(row, col)) numOpenSites++
        grid[row - 1][col - 1] = 1
        connectIfOpenInCol(row, col)
        if (row == 1) {
            if (n > 1) connectIfOpenInBelowRow(row, col)
            uf.union(0, linearized(row, col))
        }
        if (row == n) {
            if (n > 1) connectIfOpenInUpperRow(row, col)
            uf.union(n * n + 1, linearized(row, col))
        }
        if (row != 1 && row != n) {
            connectIfOpenInUpperRow(row, col)
            connectIfOpenInBelowRow(row, col)
        }
    }

    fun isOpen(row: Int, col: Int): Boolean {
        validateInput(row, col)
        return grid[row - 1][col - 1] == 1
    }

    fun isFull(row: Int, col: Int): Boolean {
        validateInput(row, col)
        return uf.connected(0, linearized(row, col))
    }

    fun numberOfOpenSites(): Int = numOpenSites
    fun percolates(): Boolean = uf.connected(0, n * n + 1)
}