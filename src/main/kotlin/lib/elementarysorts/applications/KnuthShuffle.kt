package lib.elementarysorts.applications

import edu.princeton.cs.algs4.StdRandom
import lib.utils.ArrayUtils

class KnuthShuffle {
    companion object {
        fun <T> shuffle(a: Array<T>) {
            for (i in 0..(a.size - 1)) {
                val r = StdRandom.uniform(i + 1)
                ArrayUtils.exch(a, i, r)
            }
        }
    }
}