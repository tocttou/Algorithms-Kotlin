package lib.quicksort

import edu.princeton.cs.algs4.StdRandom
import lib.utils.ArrayUtils

class Quick {
    companion object {
        private fun <T> partition(
            a: Array<T>, lo: Int, hi: Int, comparator: Comparator<T>
        ): Int {
            var i = lo
            var j = hi + 1
            while (true) {
                do {
                    if (i == hi) break
                } while (comparator.compare(a[++i], a[lo]) < 0)
                do {
                    if (j == lo) break
                } while (comparator.compare(a[--j], a[lo]) > 0)
                if (i >= j) break
                ArrayUtils.exch(a, i, j)
            }
            ArrayUtils.exch(a, lo, j)
            return j
        }

        private fun <T : Comparable<T>> sort(
            a: Array<T>, lo: Int, hi: Int, comparator: Comparator<T>
        ) {
            if (hi <= lo) return
            val j = partition(a, lo, hi, comparator) // element a[j] in correct position
            sort(a, lo, j - 1, comparator)
            sort(a, j + 1, hi, comparator)
        }

        fun <T : Comparable<T>> sort(a: Array<T>) = sort(a, naturalOrder())

        fun <T : Comparable<T>> sort(a: Array<T>, comparator: Comparator<T>) {
            StdRandom.shuffle(a)
            sort(a, 0, a.size - 1, comparator)
        }

        fun <T : Comparable<T>> select(a: Array<T>, k: Int): T = select(a, k, naturalOrder())

        fun <T> select(a: Array<T>, k: Int, comparator: Comparator<T>): T {
            StdRandom.shuffle(a)
            var lo = 0
            var hi = a.size - 1
            while (lo <= hi) {
                val j = Quick.partition(a, lo, hi, comparator)
                when {
                    j < k -> lo = j + 1
                    j > k -> hi = j - 1
                    else -> return a[k]
                }
            }
            return a[k]
        }
    }
}