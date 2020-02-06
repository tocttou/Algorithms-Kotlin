package lib.tries

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object TSTTest : Spek({
    describe("a TST implementation and a list of key-value pairs") {
        val tst = TST<Int>()
        val kv = listOf(
            Pair("she", 0),
            Pair("sells", 1),
            Pair("shells", 3),
            Pair("by", 4),
            Pair("the", 5),
            Pair("sea", 6),
            Pair("shore", 7)
        )
        for (p in kv) tst.put(p.first, p.second)
        context("running TrieST.get('she') on it") {
            it("should be equal to 0") {
                assertEquals(0, tst.get("she"))
            }
        }
        context("running TrieST.get('shore') on it") {
            it("should be equal to 7") {
                assertEquals(7, tst.get("shore"))
            }
        }
        context("running TrieST.get('shoreaaa') on it") {
            it("should be equal to null") {
                assertEquals(null, tst.get("shoreaaa"))
            }
        }
        context("running TrieST.keys() on it") {
            val testVal = listOf(
                "by", "sea", "sells", "she", "shells", "shore", "the"
            )
            it("should produce keys in sorted order") {
                assertEquals(testVal, tst.keys().toList())
            }
        }
        context("running TrieST.keysWithPrefix('sh') on it") {
            val testVal = listOf(
                "she", "shells", "shore"
            )
            it("should produce keys in sorted order statring with 'sh'") {
                assertEquals(testVal, tst.keysWithPrefix("sh").toList())
            }
        }
        context("running TrieST.longestPrefixOf('shoreaaa') on it") {
            it("should be equal to shore") {
                assertEquals("shore", tst.longestPrefixOf("shoreaaa"))
            }
        }
    }
})