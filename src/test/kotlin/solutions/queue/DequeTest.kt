package solutions.queue

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import solutions.queues.Deque
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

object DequeTest : Spek({
    describe("1. a deque of integers") {
        val deque = Deque<Int>()
        context("a series of addition and removal of elements") {
            deque.addFirst(100); deque.addFirst(10); deque.addLast(200)
            deque.removeFirst(); deque.removeLast()
            deque.removeFirst() // deque is empty
            it("on calling removeFirst(), removeLast() should produce NoSuchElementException") {
                assertFailsWith<NoSuchElementException> { deque.removeFirst() }
                assertFailsWith<NoSuchElementException> { deque.removeLast() }
            }
        }
    }
    describe("2. a deque of integers") {
        val deque = Deque<Int>()
        context("a series of addition and removal of elements") {
            deque.addFirst(100); deque.addLast(200); deque.removeFirst()
            deque.addLast(300); deque.addFirst(100); deque.removeLast()
            it("should be equal to the test value") {
                val testVal = listOf(100, 200)
                assertEquals(deque.toList(), testVal)
            }
        }
    }
    describe("3. a deque of integers") {
        val deque = Deque<Int>()
        context("stress test") {
            println("Deque stress test")
            val start = System.currentTimeMillis()
            for (i in 1..50000) deque.addFirst(i)
            for (i in 50000..100000) deque.addLast(i)
            val enqueueEnd = System.currentTimeMillis()
            println("enqueue time (100000): ${enqueueEnd - start} ms")

            deque.toList()
            val iterEnd = System.currentTimeMillis()
            println("iteration time (100000): ${iterEnd - enqueueEnd} ms")

            for (i in 1..50000) deque.removeFirst()
            for (i in 50000..100000) deque.removeLast()
            val dequeueEnd = System.currentTimeMillis()
            println("dequeue time (100000): ${dequeueEnd - iterEnd} ms")
            println("***")
        }
    }
})