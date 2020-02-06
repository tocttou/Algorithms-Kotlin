package solutions.percolation

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object PercolationTest : Spek({
    describe("a Kotlin data type Percolation") {
        val percolation = Percolation(1)
        context("performing a number of operations on it") {
            val sitesToOpen = listOf(
                Pair(1, 1)
            )
            for (site in sitesToOpen) percolation.open(site.first, site.second)
            it("should produce output equal to the test value") {
                assertEquals(percolation.percolates(), true)
                assertEquals(percolation.isFull(1, 1), true)
                assertEquals(percolation.isOpen(1, 1), true)
                assertEquals(percolation.numberOfOpenSites(), 1)
            }
        }
    }
    describe("a Kotlin data type Percolation") {
        val percolation = Percolation(5)
        context("performing a number of operations on it") {
            val sitesToOpen = listOf(
                Pair(1, 1), Pair(2, 1), Pair(2, 2), Pair(3, 2), Pair(4, 2), Pair(4, 3), Pair(5, 2)
            )
            for (site in sitesToOpen) percolation.open(site.first, site.second)
            it("should produce output equal to the test value") {
                assertEquals(percolation.percolates(), true)
                assertEquals(percolation.isFull(4, 2), true)
                assertEquals(percolation.isFull(4, 4), false)
                assertEquals(percolation.isOpen(4, 2), true)
                assertEquals(percolation.isOpen(4, 4), false)
                assertEquals(percolation.numberOfOpenSites(), 7)
            }
        }
    }
    describe("a Java data type Percolation") {
        val percolation = Percolation(5)
        context("performing a number of operations on it") {
            val sitesToOpen = listOf(
                Pair(1, 1), Pair(2, 1), Pair(2, 2), Pair(3, 2), Pair(4, 2), Pair(4, 3), Pair(5, 2)
            )
            for (site in sitesToOpen) percolation.open(site.first, site.second)
            it("should produce output equal to the test value") {
                assertEquals(percolation.percolates(), true)
                assertEquals(percolation.isFull(4, 2), true)
                assertEquals(percolation.isFull(4, 4), false)
                assertEquals(percolation.isOpen(4, 2), true)
                assertEquals(percolation.isOpen(4, 4), false)
                assertEquals(percolation.numberOfOpenSites(), 7)
            }
        }
    }
})