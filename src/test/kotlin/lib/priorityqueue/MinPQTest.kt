package lib.priorityqueue

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

object MinPQTest : Spek({
    Feature("an empty min priority queue") {
        val minpq by memoized { MinPQ<Char>() }
        Scenario("deleting min element from empty queue") {
            Then("should fail with NoSuchElementException") {
                assertFailsWith<NoSuchElementException> { minpq.delMin() }
            }
        }
        Scenario("a series of insertions") {
            When("setup") {
                arrayOf('T', 'P', 'R', 'N', 'H', 'O', 'A', 'E', 'I', 'G').forEach { minpq.insert(it) }
            }
            Then("should be equal to test test value") {
                val testVal = arrayOf('A', 'E', 'H', 'I', 'G', 'R', 'O', 'T', 'N', 'P')
                assertEquals(testVal.toList(), minpq.toList())
            }
        }
        Scenario("inserting 'S' in it") {
            When("setup") {
                arrayOf('T', 'P', 'R', 'N', 'H', 'O', 'A', 'E', 'I', 'G').forEach { minpq.insert(it) }
                minpq.insert('S')
            }
            Then("should be equal to test test value") {
                val testVal = arrayOf('A', 'E', 'H', 'I', 'G', 'R', 'O', 'T', 'N', 'P', 'S')
                assertEquals(testVal.toList(), minpq.toList())
            }
        }
        Scenario("deleting minimum from it") {
            When("setup") {
                arrayOf('T', 'P', 'R', 'N', 'H', 'O', 'A', 'E', 'I', 'G', 'S').forEach { minpq.insert(it) }
                minpq.delMin()
            }
            Then("should be equal to test test value") {
                val testVal = arrayOf('E', 'G', 'H', 'I', 'P', 'R', 'O', 'T', 'N', 'S')
                assertEquals(testVal.toList(), minpq.toList())
            }
        }
    }
})