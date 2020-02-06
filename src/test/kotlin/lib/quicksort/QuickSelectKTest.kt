package lib.quicksort

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object QuickSelectKTest : Spek({
    describe("a unsorted random integer array") {
        val unsorted = Array<Int>(10000) { Math.round(Math.random() * 10000).toInt() }
        val arrCopyOne = unsorted.copyOf()
        Quick.sort(arrCopyOne)
        context("selecting the 3rd element (index: 3) in sorted order") {
            val arrCopyTwo = unsorted.copyOf()
            val start = System.currentTimeMillis()
            val elem = Quick.select(arrCopyTwo, 3)
            val end = System.currentTimeMillis()
            println("Quick select stress test (random unsorted input)")
            println("time (10000) (element index: 3): ${end - start} ms")
            println("***")
            it("should be equal to the test value") {
                assertEquals(arrCopyOne[3], elem)
            }
        }
        context("selecting the 3000th element (index: 3000) in sorted order") {
            val arrCopyTwo = unsorted.copyOf()
            val start = System.currentTimeMillis()
            val elem = Quick.select(arrCopyTwo, 3000)
            val end = System.currentTimeMillis()
            println("Quick select stress test (random unsorted input)")
            println("time (10000) (element index: 3000): ${end - start} ms")
            println("***")
            it("should be equal to the test value") {
                assertEquals(arrCopyOne[3000], elem)
            }
        }
    }
})