package lib.substringsearch

class BoyerMoore(private val pat: String) {
    private val M = pat.length
    private val R = 256
    private val right = Array(R) { -1 }

    init {
        for (j in 0..(M - 1)) right[pat[j].toInt()] = j
    }

    fun search(txt: String): Int {
        val N = txt.length
        var skip: Int
        var i = 0
        while (i <= N - M) {
            skip = 0
            for (j in M - 1 downTo 0) {
                if (pat[j] != txt[i + j]) {
                    skip = computeSkip(j, txt, i)
                    break
                }
            }
            if (skip == 0) return i
            i += skip
        }
        return N
    }

    private fun computeSkip(j: Int, txt: String, i: Int) =
        Math.max(1, j - right[txt[i + j].toInt()])
}
