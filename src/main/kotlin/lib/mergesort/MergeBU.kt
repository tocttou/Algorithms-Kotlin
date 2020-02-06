package lib.mergesort

class MergeBU {
    companion object {
        private fun <T : Comparable<T>> merge(
            a: Array<T>, aux: Array<T>, lo: Int, mid: Int, hi: Int
        ) {
            for (i in lo..hi) aux[i] = a[i]
            var i = lo
            var j = mid + 1
            for (k in lo..hi) {
                when {
                    i > mid -> a[k] = aux[j++]
                    j > hi -> a[k] = aux[i++]
                    aux[i] <= aux[j] -> a[k] = aux[i++]
                    else -> a[k] = aux[j++]
                }
            }
        }

        fun <T : Comparable<T>> sort(a: Array<T>) {
            val aux = a.copyOf()
            var sz = 1
            val n = a.size
            while (sz < n) {
                var lo = 0
                while (lo < n - sz) {
                    merge(a, aux, lo, lo + sz - 1, Math.min(lo + 2 * sz - 1, n - 1))
                    lo += 2 * sz
                }
                sz *= 2
            }
        }
    }
}