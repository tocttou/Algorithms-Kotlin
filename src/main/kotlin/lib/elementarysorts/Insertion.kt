package lib.elementarysorts

import lib.utils.ArrayUtils

/*
  although the number of compares are less (half on average) than
  selection sort, number of swaps are order n^2 as compared to
  order n in selection sort - can be slower than selection for random arrays
  much better for almost sorted arrays (see SortTest.kt)
 */

class Insertion {
    companion object {
        fun <T> sort(a: Array<T>, comparator: Comparator<T>) {
            for (i in 1..(a.size - 1)) {
                for (j in i downTo 1) {
                    if (comparator.compare(a[j], a[j - 1]) < 0) ArrayUtils.exch(a, j, j - 1)
                    else break
                }
            }
        }

        fun <T : Comparable<T>> sort(a: Array<T>) = sort(a, naturalOrder())
    }
}