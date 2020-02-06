package lib.radixsorts

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object KeyIndexCountingTest : Spek({
    describe("a KeyIndexCounting implementation, an array of strings, and the char index to sort by") {
        val arr = arrayOf(
            "ad", "aa", "ac", "af", "af", "ab", "ad", "ab", "af", "ab", "ae", "aa"
        )
        val d = 1
        context("sorting the array") {
            val testVal = listOf(
                "aa", "aa", "ab", "ab", "ab", "ac", "ad", "ad", "ae", "af", "af", "af"
            )
            KeyIndexCounting.sort(arr, d)
            it("should be equal to the test value") {
                assertEquals(testVal, arr.toList())
            }
        }
    }
})