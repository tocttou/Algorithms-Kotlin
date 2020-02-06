package lib.elementarysorts.applications

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

/*
 this is not a test of randomness
 just a test of the working of the algorithm
 can fail easily for small arrays
*/
object KnuthShuffleTest : Spek({
    describe("an array of integers") {
        val array = Array<Int>(10000) { it }
        context("passing it through Knuth Shuffle (uniform random shuffle)") {
            val toBeShuffled = array.copyOf()
            KnuthShuffle.shuffle(toBeShuffled)
            it("most likely is not equal to the original array") {
                assertEquals(
                    false,
                    array.toList() == toBeShuffled.toList()
                )
            }
        }
    }
})