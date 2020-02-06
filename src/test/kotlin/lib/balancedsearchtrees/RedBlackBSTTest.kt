package lib.balancedsearchtrees

import edu.princeton.cs.algs4.StdRandom
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

object RedBlackBSTTest : Spek({
    Feature("1. a RedBlackBST") {
        val bst by memoized { RedBlackBST<String, Int>() }
        Scenario("putting some values") {
            When("setup") {
                bst.put("hello", 1)
                bst.put("hello", 2)
                bst.put("world", 3)
                bst.put("hello", 4)
                bst.put("hey", 5)
            }
            Then("should be equal to the test values") {
                assertEquals(listOf("hello", "hey", "world"), bst.keys().toList())
                assertEquals(listOf("world"), bst.keys("hez", "world").toList())
                assertEquals(4, bst.get("hello"))
                assertEquals(null, bst.get("hell"))
                assertEquals(true, bst.contains("hey"))
                assertEquals(false, bst.contains("he"))
                assertEquals(false, bst.isEmpty())
                assertEquals(3, bst.size())
                assertEquals(2, bst.size("hey", "world"))
                assertEquals(2, bst.size("hey", "worle"))
                assertEquals(2, bst.size("her", "worle"))
                assertEquals(3, bst.size("hed", "worle"))
                assertFailsWith<IllegalArgumentException> { bst.size("world", "hey") }
                assertEquals("hello", bst.min())
                assertEquals("world", bst.max())
                assertEquals("world", bst.floor("world"))
                assertEquals("world", bst.floor("worle"))
                assertEquals("hey", bst.floor("worl"))
                assertEquals("hey", bst.ceiling("hey"))
                assertEquals("world", bst.ceiling("hez"))
                assertEquals(2, bst.rank("world"))
                assertEquals(0, bst.rank("hello"))
                assertEquals("world", bst.select(2))
                assertEquals("hey", bst.select(1))
                assertEquals("hello", bst.select(0))
            }
        }
        Scenario("deleting some values") {
            When("setup") {
                bst.put("hello", 1)
                bst.put("hey", 2)
                bst.put("world", 3)
                bst.deleteMax()
                bst.deleteMin()
            }
            Then("should be equal to the test values") {
                assertEquals(listOf("hey"), bst.keys().toList())
                assertEquals(listOf(), bst.keys("hez", "world").toList())
                assertEquals(null, bst.get("hello"))
                assertEquals(true, bst.contains("hey"))
                assertEquals(false, bst.contains("world"))
                assertEquals(false, bst.isEmpty())
                assertEquals(1, bst.size())
                assertEquals(1, bst.size("hey", "world"))
                assertEquals(0, bst.size("hez", "world"))
                assertEquals(1, bst.size("her", "worle"))
                assertEquals("hey", bst.min())
                assertEquals("hey", bst.max())
                assertEquals("hey", bst.floor("world"))
                assertEquals("hey", bst.floor("worle"))
                assertEquals("hey", bst.ceiling("hey"))
                assertEquals(null, bst.ceiling("hez"))
                assertEquals(1, bst.rank("world"))
                assertEquals(0, bst.rank("hey"))
                assertFailsWith<IllegalArgumentException> { bst.select(2) }
                assertEquals("hey", bst.select(0))
            }
        }
    }
    Feature("2. a RedBlackBST") {
        val bst by memoized { RedBlackBST<String, Int>() }
        val randomNumbers by memoized { Array(1000) { StdRandom.uniform(1000) } }
        Scenario("stress testing it") {
            When ("stress test") {
                val start = System.currentTimeMillis()

                for (i in 0..999) bst.put(randomNumbers[i].toString(), randomNumbers[i])
                val end1 = System.currentTimeMillis()
                println("RedBlackBST stress test (put)")
                println("time (1000): ${end1 - start} ms")
                println("***")

                for (i in 0..999) bst.max()
                val end2 = System.currentTimeMillis()
                println("RedBlackBST stress test (max)")
                println("time (1000): ${end2 - end1} ms")
                println("***")

                for (i in 0..999) bst.floor("500")
                val end3 = System.currentTimeMillis()
                println("RedBlackBST stress test (floor)")
                println("time (1000): ${end3 - end2} ms")
                println("***")

                for (i in 0..999) bst.rank("500")
                val end4 = System.currentTimeMillis()
                println("RedBlackBST stress test (rank)")
                println("time (1000): ${end4 - end3} ms")
                println("***")

                for (i in 0..999) bst.select(500)
                val end5 = System.currentTimeMillis()
                println("RedBlackBST stress test (select)")
                println("time (1000): ${end5 - end4} ms")
                println("***")

                for (i in 0..999) bst.keys()
                val end6 = System.currentTimeMillis()
                println("RedBlackBST stress test (ordered iteration)")
                println("time (1000): ${end6 - end5} ms")
                println("***")
            }

            Then("keys() should produce a sorted array") {
                assertEquals(
                    true,
                    bst.keys().windowed(2).any { (a, b) -> a <= b }
                )
            }
        }
    }
})