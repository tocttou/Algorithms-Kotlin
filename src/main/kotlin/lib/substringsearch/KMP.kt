package lib.substringsearch


class KMP(pat: String) {
    private val M = pat.length
    private val R = 256
    private val dfa = Array(R) { IntArray(M) }

    init {
        dfa[pat[0].toInt()][0] = 1
        var X = 0
        var j = 1
        while (j < M) {
            for (c in 0 until R) dfa[c][j] = dfa[c][X]
            dfa[pat[j].toInt()][j] = j + 1
            X = dfa[pat[j].toInt()][X]
            j++
        }
    }

    fun search(txt: String): Int {
        val N = txt.length
        var i = 0
        var j = 0
        while (i < N && j < M) {
            j = dfa[txt[i].toInt()][j]
            i++
        }
        return if (j == M) i - M
        else N
    }
}
