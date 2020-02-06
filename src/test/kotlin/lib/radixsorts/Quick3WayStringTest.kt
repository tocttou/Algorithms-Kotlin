package lib.radixsorts

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object Quick3WayStringTest : Spek({
    describe("a Quick3WayString sort implementation and an array of strings") {
        val arr = arrayOf(
            "dab", "add", "cab", "fad", "fee", "bad", "dad", "bee", "fed", "bede", "ebb", "ace"
        )
        context("sorting the array of strings") {
            Quick3WayString.sort(arr)
            val testVal = listOf(
                "ace", "add", "bad", "bede", "bee", "cab", "dab", "dad", "ebb", "fad", "fed", "fee"
            )
            it("should be equal to the test value") {
                assertEquals(testVal, arr.toList())
            }
        }
    }
})