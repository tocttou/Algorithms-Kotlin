package lib.mergesort

class Merge {
    companion object {
        private fun <T> merge(
            a: Array<T>, aux: Array<T>, lo: Int, mid: Int, hi: Int, comparator: Comparator<T>
        ) {
            for (i in lo..hi) aux[i] = a[i]
            var i = lo
            var j = mid + 1
            for (k in lo..hi) {
                when {
                    i > mid -> a[k] = aux[j++]
                    j > hi -> a[k] = aux[i++]
                    comparator.compare(aux[i], aux[j]) <= 0 -> a[k] = aux[i++]
                    else -> a[k] = aux[j++]
                }
            }
        }

        private fun <T> sort(
            a: Array<T>, aux: Array<T>, lo: Int, hi: Int, comparator: Comparator<T>
        ) {
            if (hi <= lo) return
            val mid = lo + (hi - lo) / 2
            sort(a, aux, lo, mid, comparator)
            sort(a, aux, mid + 1, hi, comparator)
            if (comparator.compare(a[mid + 1], a[mid]) > 0) return
            merge(a, aux, lo, mid, hi, comparator)
        }

        fun <T : Comparable<T>> sort(a: Array<T>) = sort(a, naturalOrder())

        fun <T> sort(a: Array<T>, comparator: Comparator<T>) {
            val aux = a.copyOf()
            sort(a, aux, 0, a.size - 1, comparator)
        }
    }
}