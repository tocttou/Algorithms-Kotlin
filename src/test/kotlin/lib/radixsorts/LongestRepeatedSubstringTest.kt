package lib.radixsorts

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object LongestRepeatedSubstringTest : Spek({
    describe("a LongestRepeatedSubstringTest implementation and a long string") {
        val s = "itwasbestitwasw"
        context("finding the longest repeated substring") {
            it("should be equal to 'itwas'") {
                assertEquals("itwas", LongestRepeatedSubstring.lrs(s))
            }
        }
    }
})