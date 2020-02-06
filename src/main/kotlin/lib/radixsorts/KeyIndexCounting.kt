package lib.radixsorts

import lib.utils.StringUtils.charAt


object KeyIndexCounting {
    val R = 256
    fun sort(a: Array<String>, d: Int) {
        val N = a.size
        val aux = Array<String>(N) { "" }
        val count = IntArray(R + 1)
        for (i in 0 until N) count[charAt(a[i], d) + 1]++
        for (r in 0 until R) count[r + 1] += count[r]
        for (i in 0 until N) aux[count[charAt(a[i], d)]++] = a[i]
        for (i in 0 until N) a[i] = aux[i]
    }
}