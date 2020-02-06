package lib.radixsorts

// for {s} with s.size = W
object LSD {
    fun sort(a: Array<String>, W: Int) {
        for (d in (W - 1) downTo 0) {
            KeyIndexCounting.sort(a, d)
        }
    }
}