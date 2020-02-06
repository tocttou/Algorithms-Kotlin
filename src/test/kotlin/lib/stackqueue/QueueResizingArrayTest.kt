package lib.stackqueue

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

object QueueResizingArrayTest : Spek({
    Feature("a queue implementation using resizing array") {
        val queue by memoized { QueueResizingArray<String>() }
        Scenario("a series of push and pop") {
            When("setup") {
                val strings = arrayOf("hello", "world", "from", "kotlin", "language")
                for (string in strings) queue.push(string)
                repeat(3) { queue.pop() }; queue.push("test"); queue.pop()
            }
            Then("should be equal to the test value") {
                val sampleValue = queue.toList()
                assertEquals(sampleValue, listOf("language", "test"))
                assertEquals(queue.size, 2)
                assertEquals(queue.pop(), "language")
                assertEquals(queue.pop(), "test")
                assertEquals(queue.size, 0)
                assertEquals(queue.isEmpty(), true)
                assertFailsWith<NoSuchElementException> { assertEquals(queue.pop(), "") }
            }
        }
        Scenario("on stress test") {
            val start = System.currentTimeMillis()
            for (i in 0..1000000) queue.push("test")
            val pushEnd = System.currentTimeMillis()
            for (i in 0..1000000) queue.pop()
            val popEnd = System.currentTimeMillis()

            Then("should print out the elapsed time") {
                println("QueueResizingArray stress test")
                println("push time (1000000): ${pushEnd - start} ms")
                println("pop time (1000000): ${popEnd - pushEnd} ms")
                println("***")
            }
        }
    }
})