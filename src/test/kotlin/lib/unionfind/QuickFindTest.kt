package lib.unionfind

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object QuickFindTest : Spek({
    // have to reduce the range by a factor of 1000 because the test does not complete otherwise
    describe("A Union-Find implementation: QuickFind(10000)") {
        val qf = QuickFind(10000)
        context("on running operations on it") {
            for (i in 0..9997) {
                qf.union(i, i + 1)
            }
            it("should produce output equal to the test value") {
                assertEquals(qf.connected(0, 2), true)
                assertEquals(qf.connected(1, 3), true)
                assertEquals(qf.connected(1, 9999), false)
            }
        }
    }
})