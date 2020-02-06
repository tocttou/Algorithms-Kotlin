package solutions.queue

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import solutions.queues.RandomizedQueue
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

object RandomizedQueueTest : Spek({
    describe("1. a randomized queue of integers") {
        val queue = RandomizedQueue<Int>()
        context("a series of queue and dequeue") {
            queue.enqueue(200); queue.enqueue(300); queue.enqueue(400)
            queue.dequeue(); queue.enqueue(1000); queue.dequeue()
            queue.dequeue()
            queue.dequeue() // queue is empty now
            it("should throw error on dequeue(), sample() for empty queue") {
                assertFailsWith<NoSuchElementException> { queue.dequeue() }
                assertFailsWith<NoSuchElementException> { queue.sample() }
            }
        }
    }
    describe("2. a randomized queue of integers") {
        val queue = RandomizedQueue<Int>()
        context("a series of queue and dequeue") {
            queue.enqueue(1200); queue.enqueue(1600)
            val element = queue.dequeue()
            queue.enqueue(2000)
            val sample = queue.sample()
            val testValOne = mutableListOf(1200, 1600)
            testValOne.remove(element); testValOne.add(2000)
            it("sample() should be from the queue") {
                assertEquals(testValOne.contains(sample), true)
                assertEquals(testValOne.contains(2000), true)
            }
            it("iterator output should be equal to the test value") {
                val iterOut = queue.toList()
                assertEquals(
                    iterOut == listOf(1200, 2000) || iterOut == listOf(
                        2000, 1200
                    ) || iterOut == listOf(
                        1600, 2000
                    ) || iterOut == listOf(2000, 1600), true
                )
            }
        }
    }
    describe("3. a randomized queue of integers") {
        val queue = RandomizedQueue<Int>()
        context("stress test") {
            println("Randomized queue stress test")
            val start = System.currentTimeMillis()
            for (i in 1..100000) queue.enqueue(i)
            val enqueueEnd = System.currentTimeMillis()
            println("enqueue time (100000): ${enqueueEnd - start} ms")

            queue.toList()
            val iterEnd = System.currentTimeMillis()
            println("iteration time (100000): ${iterEnd - enqueueEnd} ms")

            for (i in 1..100000) queue.dequeue()
            val dequeueEnd = System.currentTimeMillis()
            println("dequeue time (100000): ${dequeueEnd - iterEnd} ms")
            println("***")
        }
    }
})