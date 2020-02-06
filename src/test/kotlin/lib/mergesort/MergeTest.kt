package lib.mergesort

import lib.utils.ArrayUtils
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object MergeTest : Spek({
    describe("a sorted integer array") {
        val array = Array<Int>(10000) { it }
        context("on passing it through merge sort") {
            val toBeSorted = array.copyOf()
            val start = System.currentTimeMillis()
            Merge.sort(toBeSorted)
            val end = System.currentTimeMillis()
            println("Merge sort stress test (already sorted input)")
            println("time (10000): ${end - start} ms")
            println("***")
            it("should be equal to the test value") {
                assertEquals(ArrayUtils.isSortedArray(toBeSorted), true)
            }
        }
    }
    describe("an unsorted random integer array") {
        val unsorted = Array<Int>(10000) { Math.round(Math.random() * 10000).toInt() }
        context("on passing it through merge sort") {
            val toBeSorted = unsorted.copyOf()
            val start = System.currentTimeMillis()
            Merge.sort(toBeSorted)
            val end = System.currentTimeMillis()
            println("Merge sort stress test (random unsorted input)")
            println("time (10000): ${end - start} ms")
            println("***")
            it("should be equal to the test value") {
                assertEquals(ArrayUtils.isSortedArray(toBeSorted), true)
                assertEquals(ArrayUtils.isSortedArray(unsorted), false)
            }
        }
    }
})