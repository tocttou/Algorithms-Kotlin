package lib.substringsearch

object BruteForce {
    fun search(pat: String, txt: String): Int {
        val N = txt.length
        val M = pat.length
        var i = 0
        var j = 0
        while (i < N && j < M) {
            if (txt[i] == pat[j]) j++
            else {
                i -= j
                j = 0
            }
            i++
        }
        return if (j == M) i - M
        else N
    }
}