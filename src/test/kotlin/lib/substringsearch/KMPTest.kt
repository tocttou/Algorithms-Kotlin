package lib.substringsearch

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object KMPTest : Spek({
    describe("a KMP substring search implemenation, a text, and a pattern") {
        val text = "ABAAAABAAAAAAAAA"
        val pattern = "BAAAAAAAAA"
        val kmp = KMP(pattern)
        context("running kmp.search() on these") {
            it("should be equal to 6") {
                assertEquals(6, kmp.search(text))
            }
        }
    }
})