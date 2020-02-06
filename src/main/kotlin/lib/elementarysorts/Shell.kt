package lib.elementarysorts

import lib.utils.ArrayUtils

/*
 In insertion sort, we have to the swap elements consecutively several times
 even when we know that the new element has to be inserted at a long distance. To mitigate
 this problem to some extent, we can use shell sort which works by moving the entries
 several position at a time (instead of 1 at a time as in insertion sort)
 */

class Shell {
    companion object {
        fun <T> sort(a: Array<T>, comparator: Comparator<T>) {

            /*
             shell sort is much faster than selection sort and insertion sort for
             random array, insertion sort might be *faster* than shell sort for
             partially sorted array (the more already sorted, the more is insertion
             sort faster).

             The below commented out code is valid for shell sort but the Kotlin idioms
             introduce considerable latency, which makes this code slower than insertion sort
             and selection sort!! The implementation using while loops is much better.
             */

            /*
             val hseq = generateSequence(1) { 3 * it + 1 }
             hseq.takeWhile { it < a.size / 3 }.forEach {
                 for (i in it..(a.size - 1)) {
                     for (j in i downTo it step it) {
                         if (a[j] < a[j - it]) ArrayUtils.exch(a, j, j - it)
                         else break
                     }
                 }
             }
             */

            var h = 1
            while (h < a.size / 3) h = 3 * h + 1 // generate sequence x -> x = 3x + 1
            while (h >= 1) {
                for (i in h..(a.size - 1)) {
                    for (j in i downTo h step h) {
                        if (comparator.compare(a[j], a[j - h]) < 0) ArrayUtils.exch(a, j, j - h)
                        else break
                    }
                }
                h /= 3
            }
        }

        fun <T : Comparable<T>> sort(a: Array<T>) = sort(a, naturalOrder())
    }
}