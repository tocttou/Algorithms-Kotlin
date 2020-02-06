package lib.hashtable

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object SeparateChainingHashSTTest : Spek({
    describe("a SeparateChainingHashST implementation") {
        val separateChainingHashST = SeparateChainingHashST<Char, Int>(13)
        "SEARCHEXAMPLE".mapIndexed { index, c -> separateChainingHashST.put(c, index) }
        context("performing get on it") {
            it("should be equal to the test value") {
                assertEquals(4, separateChainingHashST.get('C'))
                assertEquals(0, separateChainingHashST.get('S'))
            }
        }
    }
})