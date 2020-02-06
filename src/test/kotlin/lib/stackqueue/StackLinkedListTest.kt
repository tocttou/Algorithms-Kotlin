package lib.stackqueue

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

object StackLinkedListTest : Spek({
    describe("a stack implementation using linked list") {
        val stack = StackLinkedList<String>()
        context("a series of push and pop") {
            val strings = arrayOf("hello", "world", "from", "kotlin", "language")
            for (string in strings) stack.push(string)
            repeat(3, { stack.pop() }); stack.push("test"); stack.pop()
            it("should be equal to the test value") {
                val sampleValue = stack.toList()
                assertEquals(listOf("world", "hello"), sampleValue)
                assertEquals(2, stack.size)
                assertEquals("world", stack.pop())
                assertEquals("hello", stack.pop())
                assertEquals(0, stack.size)
                assertEquals(true, stack.isEmpty())
                assertFailsWith<NoSuchElementException> { assertEquals("", stack.pop()) }
            }
        }
        context("on stress test") {
            val start = System.currentTimeMillis()
            for (i in 0..1000000) stack.push("test")
            val pushEnd = System.currentTimeMillis()
            for (i in 0..1000000) stack.pop()
            val popEnd = System.currentTimeMillis()
            it("should print out the elapsed time") {
                println("StackLinkedList stress test")
                println("push time (1000000): ${pushEnd - start} ms")
                println("pop time (1000000): ${popEnd - pushEnd} ms")
                println("***")
            }
        }
    }
})