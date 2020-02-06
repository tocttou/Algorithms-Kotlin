package lib.substringsearch


class RabinKarp(pat: String) {
    private val patHash: Long
    private val M = pat.length
    private val Q = 982_451_653
    private val R = 256
    private var RM = 1L

    init {
        for (i in 1..(M - 1)) RM = R * RM % Q
        patHash = hash(pat, M)
    }

    private fun hash(key: String, M: Int): Long {
        var h = 0L
        for (j in 0 until M) h = (R * h + key[j].toInt()) % Q
        return h
    }

    fun search(txt: String): Int {
        val N = txt.length
        var txtHash = hash(txt, M)
        if (patHash == txtHash) return 0
        for (i in M until N) {
            txtHash = (txtHash + Q - RM * txt[i - M].toInt() % Q) % Q
            txtHash = (txtHash * R + txt[i].toInt()) % Q
            if (patHash == txtHash) return i - M + 1
        }
        return N
    }
}
