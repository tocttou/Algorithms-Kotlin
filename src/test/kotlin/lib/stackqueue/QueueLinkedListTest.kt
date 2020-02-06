package lib.stackqueue

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

object QueueLinkedListTest : Spek({
    Feature("a queue implementation using linked list") {
        val queue by memoized { QueueLinkedList<String>() }
        Scenario("a series of push and pop") {
            When("setup") {
                val strings = arrayOf("hello", "world", "from", "kotlin", "language")
                for (string in strings) queue.push(string)
                repeat(3) { queue.pop() }; queue.push("test"); queue.pop()
            }
            Then("should be equal to the test value") {
                val sampleValue = queue.toList()
                assertEquals(listOf("language", "test"), sampleValue)
                assertEquals(2, queue.size)
                assertEquals("language", queue.pop())
                assertEquals("test", queue.pop())
                assertEquals(0, queue.size)
                assertEquals(true, queue.isEmpty())
                assertFailsWith<NoSuchElementException> { assertEquals("", queue.pop()) }
            }
        }
        Scenario("on stress test") {
            val start = System.currentTimeMillis()
            for (i in 0..1000000) queue.push("test")
            val pushEnd = System.currentTimeMillis()
            for (i in 0..1000000) queue.pop()
            val popEnd = System.currentTimeMillis()
            Then("should print out the elapsed time") {
                println("QueueLinkedList stress test")
                println("push time (1000000): ${pushEnd - start} ms")
                println("pop time (1000000): ${popEnd - pushEnd} ms")
                println("***")
            }
        }
    }
})