package solutions.wordnet

import lib.utils.ResourceUtils.resolve
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object OutcastTest : Spek({
    val wordnet = WordNet(
        resolve("/wordnet/synsets.txt"),
        resolve("/wordnet/hypernyms.txt")
    )
    describe("1. a wordnet implementation and an array of nouns") {
        val nouns = arrayOf(
            "apple",
            "pear",
            "peach",
            "banana",
            "lime",
            "lemon",
            "blueberry",
            "strawberry",
            "mango",
            "watermelon",
            "potato"
        )
        context("finding the outcast in the list of nouns") {
            val outcast = Outcast(wordnet).outcast(nouns)
            it("should be equal to the test value") {
                assertEquals("potato", outcast)
            }
        }
    }
    describe("2. a wordnet implementation and an array of nouns") {
        val nouns = arrayOf(
            "apple",
            "pear",
            "peach",
            "banana",
            "lime",
            "lemon",
            "blueberry",
            "strawberry",
            "mango",
            "table",
            "potato"
        )
        context("finding the outcast in the list of nouns") {
            val outcast = Outcast(wordnet).outcast(nouns)
            it("should be equal to the test value") {
                assertEquals("table", outcast)
            }
        }
    }
    describe("3. a wordnet implementation and an array of nouns") {
        val nouns = arrayOf(
            "apple",
            "pear",
            "peach",
            "banana",
            "lime",
            "anger",
            "blueberry",
            "strawberry",
            "mango",
            "table",
            "potato"
        )
        context("finding the outcast in the list of nouns") {
            val outcast = Outcast(wordnet).outcast(nouns)
            it("should be equal to the test value") {
                assertEquals("anger", outcast)
            }
        }
    }
})