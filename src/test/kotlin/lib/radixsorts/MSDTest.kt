package lib.radixsorts

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object MSDTest : Spek({
    describe("an MSD string sort implementation and an array of strings") {
        val arr = arrayOf(
            "dab", "add", "cab", "fad", "fee", "bad", "dad", "bee", "fed", "bede", "ebb", "ace"
        )
        context("sorting the array of strings") {
            MSD.sort(arr)
            val testVal = listOf(
                "ace", "add", "bad", "bede", "bee", "cab", "dab", "dad", "ebb", "fad", "fed", "fee"
            )
            it("should be equal to the test value") {
                assertEquals(testVal, arr.toList())
            }
        }
    }
})