package solutions.percolation

import edu.princeton.cs.algs4.StdRandom
import edu.princeton.cs.algs4.StdStats

class PercolationStats(n: Int, private val trials: Int) {
    private val fractionOpenSites = DoubleArray(trials)

    init {
        if (n < 1 || trials < 1) throw IllegalArgumentException("n and trials must be > 1")
        for (trial in 1..trials) {
            val percolation = Percolation(n)
            while (!percolation.percolates()) {
                percolation.open(
                    StdRandom.uniform(n) + 1, StdRandom.uniform(n) + 1
                )
            }
            fractionOpenSites[trial - 1] = percolation.numberOfOpenSites().toDouble() / (n * n)
        }
    }

    fun mean(): Double = StdStats.mean(fractionOpenSites)
    fun stddev(): Double = if (trials == 1) Double.NaN else StdStats.stddev(fractionOpenSites)
    fun confidenceLo(): Double =
        if (trials == 1) Double.NaN else mean() - (1.96 * stddev() / Math.sqrt(trials.toDouble()))

    fun confidenceHi(): Double =
        if (trials == 1) Double.NaN else mean() + (1.96 * stddev() / Math.sqrt(trials.toDouble()))

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val n = args[0].toInt()
            val t = args[1].toInt()
            val stats = PercolationStats(n, t)
            println("mean                    = ${stats.mean()}")
            println("stddev                  = ${stats.stddev()}")
            println("95% confidence interval = [${stats.confidenceLo()}, ${stats.confidenceHi()}]")
        }
    }
}
