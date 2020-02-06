package lib.radixsorts

object LongestRepeatedSubstring {
    fun lrs(s: String): String {
        val N = s.length
        val suffixes = Array(N) { s.substring(it, N) }
        var lrs = ""
        fun lcp(s1: String, s2: String): Int {
            var count = 0
            val minCount = minOf(s1.length, s2.length)
            while (count < minCount && s1[count] == s2[count]) count++
            return count
        }

        Quick3WayString.sort(suffixes)
        for (i in 0..(N - 2)) {
            val len = lcp(suffixes[i], suffixes[i + 1])
            if (len > lrs.length) {
                lrs = suffixes[i].substring(0, len)
            }
        }

        return lrs
    }
}