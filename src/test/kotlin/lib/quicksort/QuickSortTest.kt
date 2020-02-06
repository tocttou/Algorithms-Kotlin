package lib.quicksort

import lib.utils.ArrayUtils
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object QuickSortTest : Spek({
    describe("a sorted integer array") {
        val array = Array<Int>(10000) { it }
        context("on passing it through quick sort") {
            val toBeSorted = array.copyOf()
            val start = System.currentTimeMillis()
            Quick.sort(toBeSorted)
            val end = System.currentTimeMillis()
            println("Quick sort stress test (already sorted input)")
            println("time (10000): ${end - start} ms")
            println("***")
            it("should be equal to the test value") {
                assertEquals(ArrayUtils.isSortedArray(toBeSorted), true)
            }
        }
    }
    describe("an unsorted random integer array") {
        val unsorted = Array<Int>(10000) { Math.round(Math.random() * 10000).toInt() }
        context("on passing it through quick sort") {
            val toBeSorted = unsorted.copyOf()
            val start = System.currentTimeMillis()
            Quick.sort(toBeSorted)
            val end = System.currentTimeMillis()
            println("Quick sort stress test (random unsorted input)")
            println("time (10000): ${end - start} ms")
            println("***")
            it("should be equal to the test value") {
                assertEquals(ArrayUtils.isSortedArray(toBeSorted), true)
                assertEquals(ArrayUtils.isSortedArray(unsorted), false)
            }
        }
    }
})