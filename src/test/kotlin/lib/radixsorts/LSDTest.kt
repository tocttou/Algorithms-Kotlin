package lib.radixsorts

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object LSDTest : Spek({
    describe("an LSD string sort implementation and an array of strings") {
        val arr = arrayOf(
            "dab", "add", "cab", "fad", "fee", "bad", "dad", "bee", "fed", "bed", "ebb", "ace"
        )
        context("sorting the array of strings") {
            LSD.sort(arr, 3)
            val testVal = listOf(
                "ace", "add", "bad", "bed", "bee", "cab", "dab", "dad", "ebb", "fad", "fed", "fee"
            )
            it("should be equal to the test value") {
                assertEquals(testVal, arr.toList())
            }
        }
    }
})