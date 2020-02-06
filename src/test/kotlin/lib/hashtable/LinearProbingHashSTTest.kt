package lib.hashtable

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object LinearProbingHashSTTest : Spek({
    describe("a LinearProbingHashST implementation") {
        val linearProbingHashST = LinearProbingHashST<Char, Int>(13)
        "SEARCHEXAMPLE".mapIndexed { index, c -> linearProbingHashST.put(c, index) }
        context("performing get on it") {
            it("should be equal to the test value") {
                assertEquals(4, linearProbingHashST.get('C'))
                assertEquals(0, linearProbingHashST.get('S'))
            }
        }
    }
})