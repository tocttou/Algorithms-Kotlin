package lib.priorityqueue

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

object MaxPQTest : Spek({
    Feature("an empty max priority queue") {
        val maxpq by memoized { MaxPQ<Char>() }
        Scenario("deleting max element from empty queue") {
            Then("should fail with NoSuchElementException") {
                assertFailsWith<NoSuchElementException> { maxpq.delMax() }
            }
        }
        Scenario("a series of insertions") {
            When("setup") {
                arrayOf('T', 'P', 'R', 'N', 'H', 'O', 'A', 'E', 'I', 'G').forEach { maxpq.insert(it) }
            }

            Then("should be equal to test test value") {
                val testVal = arrayOf('T', 'P', 'R', 'N', 'H', 'O', 'A', 'E', 'I', 'G')
                assertEquals(testVal.toList(), maxpq.toList())
            }
        }
        Scenario("inserting 'S' in it") {
            When("setup") {
                arrayOf('T', 'P', 'R', 'N', 'H', 'O', 'A', 'E', 'I', 'G').forEach { maxpq.insert(it) }
                maxpq.insert('S')
            }
            Then("should be equal to test test value") {
                val testVal = arrayOf('T', 'S', 'R', 'N', 'P', 'O', 'A', 'E', 'I', 'G', 'H')
                assertEquals(testVal.toList(), maxpq.toList())
            }
        }
        Scenario("deleting maximum from it") {
            When("setup") {
                arrayOf('T', 'P', 'R', 'N', 'H', 'O', 'A', 'E', 'I', 'G', 'S').forEach { maxpq.insert(it) }
                maxpq.delMax()
            }
            Then("should be equal to test test value") {
                val testVal = arrayOf('S', 'P', 'R', 'N', 'H', 'O', 'A', 'E', 'I', 'G')
                assertEquals(testVal.toList(), maxpq.toList())
            }
        }
    }
})