package lib.substringsearch

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object BruteForceTest : Spek({
    describe("a BruteForce substring search implemenation, a text, and a pattern") {
        val text = "ABAAAABAAAAAAAAA"
        val pattern = "BAAAAAAAAA"
        context("running BruteForce.search() on these") {
            it("should be equal to 6") {
                assertEquals(6, BruteForce.search(pattern, text))
            }
        }
    }
})