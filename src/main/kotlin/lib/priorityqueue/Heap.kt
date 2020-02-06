package lib.priorityqueue

import lib.utils.ArrayUtils

class Heap {
    companion object {

        fun <T : Comparable<T>> sort(a: Array<T>) = sort(a, naturalOrder())

        fun <T : Comparable<T>> sort(a: Array<T>, comparator: Comparator<T>) {
            for (k in a.size / 2 downTo 1) sink(a, k, a.size, comparator) // max-heap construction
            var counter = a.size
            while (counter > 1) {
                ArrayUtils.exch(a, 0, --counter)
                sink(a, 1, counter, comparator)
            }
        }

        private fun <T : Comparable<T>> sink(
            a: Array<T>, k: Int, size: Int, comparator: Comparator<T>
        ) {
            var oneBasedIndex = k
            while (2 * oneBasedIndex <= size) {
                val j = 2 * oneBasedIndex
                val currentValue = a[oneBasedIndex - 1]
                val oneBasedIndexToSwitch = when (j) {
                    size -> j
                    else -> if (comparator.compare(a[j - 1], a[j]) > 0) j else j + 1
                }
                if (comparator.compare(currentValue, a[oneBasedIndexToSwitch - 1]) < 0) {
                    ArrayUtils.exch(a, oneBasedIndex - 1, oneBasedIndexToSwitch - 1)
                    oneBasedIndex = oneBasedIndexToSwitch
                } else break
            }
        }
    }
}