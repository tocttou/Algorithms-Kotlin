package lib.elementarysorts

import lib.utils.ArrayUtils

class Selection {
    companion object {
        fun <T> sort(a: Array<T>, comparator: Comparator<T>) {
            for (i in 0..(a.size - 1)) {
                var minIndex = i
                for (j in (i + 1)..(a.size - 1)) if (comparator.compare(
                        a[j], a[minIndex]
                    ) < 0
                ) minIndex = j
                ArrayUtils.exch(a, i, minIndex)
            }
        }

        fun <T : Comparable<T>> sort(a: Array<T>) = sort(a, naturalOrder())
    }
}