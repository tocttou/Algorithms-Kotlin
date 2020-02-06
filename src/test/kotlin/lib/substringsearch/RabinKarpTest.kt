package lib.substringsearch

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object RabinKarpTest : Spek({
    describe("a RabinKarp substring search implemenation, a text, and a pattern") {
        val text = "ABAAAABAAAAAAAAA"
        val pattern = "BAAAAAAAAA"
        val rabinKarp = RabinKarp(pattern)
        context("running rabinKarp.search() on these") {
            it("should be equal to 6") {
                assertEquals(6, rabinKarp.search(text))
            }
        }
    }
})