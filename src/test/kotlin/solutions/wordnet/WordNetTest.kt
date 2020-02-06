package solutions.wordnet

import lib.utils.ResourceUtils.resolve
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

object WordNetTest : Spek({
    describe("a WordNet implementation") {
        val wordnet = WordNet(
            resolve("/wordnet/synsets.txt"),
            resolve("/wordnet/hypernyms.txt")
        )
        context("checking the presence of nouns") {
            val nouns = mapOf(
                Pair("1850s", true), Pair("zymolysis", true), Pair("zymolysissss", false)
            )
            it("should be equal to the test value") {
                for (noun in nouns) {
                    assertEquals(noun.value, wordnet.isNoun(noun.key))
                }
            }
        }
        context("checking the lowest common ancestor of 'transition' and 'miracle'") {
            val ancestor = wordnet.sap("transition", "miracle")
            it("should be equal to the test value") {
                assertEquals("happening occurrence occurrent natural_event", ancestor)
            }
        }
        context("checking the distance between 'transition' and 'miracle'") {
            val distance = wordnet.distance("transition", "miracle")
            it("should be equal to the test value") {
                assertEquals(3, distance)
            }
        }
        context("checking the lowest common ancestor of 'miracle' and 'event'") {
            val ancestor = wordnet.sap("miracle", "event")
            it("should be equal to the test value") {
                assertEquals("event", ancestor)
            }
        }
        context("checking the distance between 'miracle' and 'event'") {
            val distance = wordnet.distance("miracle", "event")
            it("should be equal to the test value") {
                assertEquals(1, distance)
            }
        }
        context("checking the LCA of <non-existent-noun> and 'miracle'") {
            it("should fail with illegal argument exception") {
                assertFailsWith<IllegalArgumentException> { wordnet.sap("transitionwa", "miracle") }
            }
        }
    }
})