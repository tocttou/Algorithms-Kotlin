package lib.utils

object StringUtils {
    fun charAt(s: String, d: Int): Int {
        return if (d < s.length) s[d].toInt() else -1
    }
}