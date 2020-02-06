package lib.unionfind

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object WeightedQuickUnionTest : Spek({
    describe("A Union-Find implementation: WeightedQuickUnion(10000000)") {
        val wqu = WeightedQuickUnion(10000000)
        context("on running operations on it") {
            for (i in 0..9999997) {
                wqu.union(i, i + 1)
            }
            it("should produce output equal to the test value") {
                assertEquals(true, wqu.connected(0, 2))
                assertEquals(true, wqu.connected(1, 3))
                assertEquals(false, wqu.connected(1, 9999999))
            }
        }
    }
})