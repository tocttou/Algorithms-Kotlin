package lib.analysisofalgorithms

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object BinarySearchTest : Spek({
    describe("a sorted integer array") {
        val sorted = Array<Int>(7) { it + 1 }
        context("running binary search of element 7 on it") {
            val index = BinarySearch.indexOf(sorted, 7)
            it("should be equal to the test value") {
                val testVal = 6
                assertEquals(testVal, index)
            }
        }
    }
    describe("an unsorted integer array") {
        val unsorted = arrayOf(1, 3, 2, 9, 5, 4, 7)
        context("running binary search of element 7 on it") {
            val index = BinarySearch.indexOf(unsorted, 7)
            it("should be equal to the test value") {
                val testVal = -1
                assertEquals(testVal, index)
            }
        }
    }
})