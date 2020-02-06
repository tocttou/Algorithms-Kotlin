package lib.analysisofalgorithms

class BinarySearch {
    companion object {
        fun <T> indexOf(a: Array<T>, element: T, comparator: Comparator<T>): Int {
            var lo = 0
            var hi = a.size - 1
            while (lo <= hi) {
                val mid = lo + (hi - lo) / 2
                when {
                    comparator.compare(a[mid], element) < 0 -> lo = mid + 1
                    comparator.compare(a[mid], element) > 0 -> hi = mid - 1
                    else -> return mid
                }
            }
            return -1
        }

        fun <T : Comparable<T>> indexOf(a: Array<T>, element: T) =
            indexOf(a, element, naturalOrder())
    }
}