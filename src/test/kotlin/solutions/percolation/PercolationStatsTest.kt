package solutions.percolation

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object PercolationStatsTest : Spek({
    describe("Kotlin: the size of percolation object and number of trials") {
        val stats = PercolationStats(200, 100)
        context("running trials on the percolation object") {
            val mean = stats.mean()
            val stddev = stats.stddev()
            val confidenceLo = stats.confidenceLo()
            val confidenceHi = stats.confidenceHi()
            it("should give output equal to the test value") {
                assert(mean > 0.57 && mean < 0.62)
                assert(stddev > 0.001 && stddev < 0.05)
                assert(confidenceLo < mean)
                assert(confidenceHi > mean)
            }
        }
    }
    describe("Java: the size of percolation object and number of trials") {
        val stats = PercolationStats(200, 100)
        context("running trials on the percolation object") {
            val mean = stats.mean()
            val stddev = stats.stddev()
            val confidenceLo = stats.confidenceLo()
            val confidenceHi = stats.confidenceHi()
            it("should give output equal to the test value") {
                assert(mean > 0.57 && mean < 0.62)
                assert(stddev > 0.001 && stddev < 0.05)
                assert(confidenceLo < mean)
                assert(confidenceHi > mean)
            }
        }
    }
})