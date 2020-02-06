package lib.stackqueue

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

object StackResizingArrayTest : Spek({
    describe("a stack implementation using resizing array") {
        val stack = StackResizingArray<String>()
        context("a series of push and pop") {
            val strings = arrayOf("hello", "world", "from", "kotlin", "language")
            for (string in strings) stack.push(string)
            repeat(3, { stack.pop() }); stack.push("test"); stack.pop()
            it("should be equal to the test value") {
                val sampleValue = stack.toList()
                assertEquals(sampleValue, listOf("world", "hello"))
                assertEquals(stack.size, 2)
                assertEquals(stack.pop(), "world")
                assertEquals(stack.pop(), "hello")
                assertEquals(stack.isEmpty(), true)
                assertEquals(stack.size, 0)
                assertFailsWith<NoSuchElementException> { assertEquals(stack.pop(), "") }
            }
        }
        context("on stress test") {
            val start = System.currentTimeMillis()
            for (i in 0..1000000) stack.push("test")
            val pushEnd = System.currentTimeMillis()
            for (i in 0..1000000) stack.pop()
            val popEnd = System.currentTimeMillis()
            it("should print out the elapsed time") {
                println("StackResizingArray stress test")
                println("push time (1000000): ${pushEnd - start} ms")
                println("pop time (1000000): ${popEnd - pushEnd} ms")
                println("***")
            }
        }
    }
})