package lib.unionfind

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object WQUPCDoublePassTest : Spek({
    describe("A Union-Find implementation: WQUPCDoublePass(10000000)") {
        val wqupcdp = WQUPCDoublePass(10000000)
        context("on running operations on it") {
            for (i in 0..9999997) {
                wqupcdp.union(i, i + 1)
            }
            it("should produce output equal to the test value") {
                assertEquals(wqupcdp.connected(0, 2), true)
                assertEquals(wqupcdp.connected(1, 3), true)
                assertEquals(wqupcdp.connected(1, 9999999), false)
            }
        }
    }
})