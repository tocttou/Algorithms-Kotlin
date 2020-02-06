package lib.radixsorts

import lib.utils.ArrayUtils.exch
import lib.utils.StringUtils.charAt

object Quick3WayString {
    fun sort(a: Array<String>) {
        sort(a, 0, a.size - 1, 0)
    }

    private fun sort(a: Array<String>, lo: Int, hi: Int, d: Int) {
        if (hi <= lo) return
        var lt = lo
        var gt = hi
        val v = charAt(a[lo], d)
        var i = lo + 1
        while (i <= gt) {
            val t = charAt(a[i], d)
            if (t < v) exch(a, lt++, i++)
            else if (t > v) exch(a, i, gt--)
            else i++
        }
        sort(a, lo, lt - 1, d)
        if (v >= 0) sort(a, lt, gt, d + 1)
        sort(a, gt + 1, hi, d)
    }
}