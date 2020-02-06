package lib.utils

object ArrayUtils {
    fun <T> exch(a: Array<T>, i: Int, j: Int) {
        val temp = a[i]
        a[i] = a[j]
        a[j] = temp
    }

    fun <T : Comparable<T>> isSortedArray(
        a: Array<T>, comparator: Comparator<T> = naturalOrder()
    ): Boolean {
        for (i in a.withIndex().windowed(2)) {
            if (comparator.compare(i[0].value, i[1].value) > 0) return false
        }
        return true
    }
}
