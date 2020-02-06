package lib.radixsorts

import lib.utils.StringUtils.charAt

object MSD {
    private val R = 256
    fun sort(a: Array<String>) {
        val aux = Array<String>(a.size) { "" }
        sort(a, aux, 0, a.size - 1, 0)
    }

    private fun sort(a: Array<String>, aux: Array<String>, lo: Int, hi: Int, d: Int) {
        if (hi <= lo) return
        val count = IntArray(R + 2)
        for (i in lo..hi) count[charAt(a[i], d) + 2]++
        for (r in 0 until R + 1) count[r + 1] += count[r]
        for (i in lo..hi) aux[count[charAt(a[i], d) + 1]++] = a[i]
        for (i in lo..hi) a[i] = aux[i - lo]
        for (r in 0 until R) sort(a, aux, lo + count[r], lo + count[r + 1] - 1, d + 1)
    }
}
