package lib.utils

object ResourceUtils {
    fun resolve(path: String): String {
        return this::class.java.getResource(path).file
    }
}