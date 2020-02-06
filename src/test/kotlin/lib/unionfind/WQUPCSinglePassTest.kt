package lib.unionfind

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object WQUPCSinglePassTest : Spek({
    describe("A Union-Find implementation: WQUPCSinglePass(10000000)") {
        val wqupcsp = WQUPCSinglePass(10000000)
        context("on running operations on it") {
            for (i in 0..9999997) {
                wqupcsp.union(i, i + 1)
            }
            it("should produce output equal to the test value") {
                assertEquals(wqupcsp.connected(0, 2), true)
                assertEquals(wqupcsp.connected(1, 3), true)
                assertEquals(wqupcsp.connected(1, 9999999), false)
            }
        }
    }
})