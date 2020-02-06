package lib.unionfind

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object QuickUnionTest : Spek({
    describe("A Union-Find implementation: QuickUnion(10000000)") {
        val qu = QuickUnion(10000000)
        context("on running operations on it") {
            for (i in 0..9999997) {
                qu.union(i, i + 1)
            }
            it("should produce output equal to the test value") {
                assertEquals(qu.connected(0, 2), true)
                assertEquals(qu.connected(1, 3), true)
                assertEquals(qu.connected(1, 9999999), false)
            }
        }
    }
})