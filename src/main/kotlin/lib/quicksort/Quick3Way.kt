package lib.quicksort

import edu.princeton.cs.algs4.StdRandom
import lib.utils.ArrayUtils

class Quick3Way {
    companion object {
        private fun <T> partition(
            a: Array<T>, lo: Int, hi: Int, comparator: Comparator<T>
        ): Pair<Int, Int> {
            var i = lo
            var lt = lo
            var gt = hi
            val v = a[lo]

            while (i <= gt) {
                when {
                    comparator.compare(a[i], v) < 0 -> ArrayUtils.exch(a, i++, lt++)
                    comparator.compare(a[i], v) > 0 -> ArrayUtils.exch(a, i, gt--)
                    else -> i++
                }
            }

            return Pair(lt, gt)
        }

        fun <T : Comparable<T>> sort(a: Array<T>) = sort(a, naturalOrder())

        fun <T : Comparable<T>> sort(a: Array<T>, comparator: Comparator<T>) {
            StdRandom.shuffle(a)
            sort(a, 0, a.size - 1, comparator)
        }

        private fun <T : Comparable<T>> sort(
            a: Array<T>, lo: Int, hi: Int, comparator: Comparator<T>
        ) {
            if (hi <= lo) return
            val (lt, gt) = partition(a, lo, hi, comparator)
            sort(a, lo, lt - 1, comparator)
            sort(a, gt + 1, hi, comparator)
        }

        fun <T : Comparable<T>> select(a: Array<T>, k: Int) = select(a, k, naturalOrder())

        fun <T> select(a: Array<T>, k: Int, comparator: Comparator<T>): T {
            StdRandom.shuffle(a)
            var lo = 0
            var hi = a.size - 1
            while (hi >= lo) {
                val (lt, gt) = partition(a, lo, hi, comparator)
                when (k) {
                    in lt..gt -> return a[k]
                    in lo..(lt - 1) -> hi = lt - 1
                    in (gt + 1)..hi -> lo = gt + 1
                }
            }
            return a[k]
        }
    }
}