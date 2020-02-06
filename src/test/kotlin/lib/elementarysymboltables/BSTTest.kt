package lib.elementarysymboltables

import edu.princeton.cs.algs4.StdRandom
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

object BSTTest : Spek({
    Feature("1. a BST") {
        val bst by memoized { BST<String, Int>() }
        Scenario("putting some values") {
            When("setup") {
                bst.put("hello", 1)
                bst.put("hello", 2)
                bst.put("world", 3)
                bst.put("hello", 4)
                bst.put("hey", 5)
            }
            Then("should be equal to the test values") {
                assertEquals(bst.keys().toList(), listOf("hello", "hey", "world"))
                assertEquals(bst.keys("hez", "world").toList(), listOf("world"))
                assertEquals(bst.get("hello"), 4)
                assertEquals(bst.get("hell"), null)
                assertEquals(bst.contains("hey"), true)
                assertEquals(bst.contains("he"), false)
                assertEquals(bst.isEmpty(), false)
                assertEquals(bst.size(), 3)
                assertEquals(bst.size("hey", "world"), 2)
                assertEquals(bst.size("hey", "worle"), 2)
                assertEquals(bst.size("her", "worle"), 2)
                assertEquals(bst.size("hed", "worle"), 3)
                assertFailsWith<IllegalArgumentException> { bst.size("world", "hey") }
                assertEquals(bst.min(), "hello")
                assertEquals(bst.max(), "world")
                assertEquals(bst.floor("world"), "world")
                assertEquals(bst.floor("worle"), "world")
                assertEquals(bst.floor("worl"), "hey")
                assertEquals(bst.ceiling("hey"), "hey")
                assertEquals(bst.ceiling("hez"), "world")
                assertEquals(bst.rank("world"), 2)
                assertEquals(bst.rank("hello"), 0)
                assertEquals(bst.select(2), "world")
                assertEquals(bst.select(1), "hey")
                assertEquals(bst.select(0), "hello")
            }
        }
        Scenario("deleting some values") {
            When("setup") {
                bst.put("hello", 1)
                bst.put("world", 2)
                bst.put("hey", 3)
                bst.deleteMax()
                bst.deleteMin()
            }
            Then("should be equal to the test values") {
                assertEquals(bst.keys().toList(), listOf("hey"))
                assertEquals(bst.keys("hez", "world").toList(), listOf())
                assertEquals(bst.get("hello"), null)
                assertEquals(bst.contains("hey"), true)
                assertEquals(bst.contains("world"), false)
                assertEquals(bst.isEmpty(), false)
                assertEquals(bst.size(), 1)
                assertEquals(bst.size("hey", "world"), 1)
                assertEquals(bst.size("hez", "world"), 0)
                assertEquals(bst.size("her", "worle"), 1)
                assertEquals(bst.min(), "hey")
                assertEquals(bst.max(), "hey")
                assertEquals(bst.floor("world"), "hey")
                assertEquals(bst.floor("worle"), "hey")
                assertEquals(bst.ceiling("hey"), "hey")
                assertEquals(bst.ceiling("hez"), null)
                assertEquals(bst.rank("world"), 1)
                assertEquals(bst.rank("hey"), 0)
                assertFailsWith<IllegalArgumentException> { bst.select(2) }
                assertEquals(bst.select(0), "hey")
            }
        }
    }
    Feature("2. a BST") {
        val bst by memoized { BST<String, Int>() }
        val randomNumbers by memoized { Array(1000) { StdRandom.uniform(1000) } }
        Scenario("stress testing it") {
            When("stress test") {
                val start = System.currentTimeMillis()

                for (i in 0..999) bst.put(randomNumbers[i].toString(), randomNumbers[i])
                val end1 = System.currentTimeMillis()
                println("BST stress test (put)")
                println("time (1000): ${end1 - start} ms")
                println("***")

                for (i in 0..999) bst.max()
                val end2 = System.currentTimeMillis()
                println("BST stress test (max)")
                println("time (1000): ${end2 - end1} ms")
                println("***")

                for (i in 0..999) bst.floor("500")
                val end3 = System.currentTimeMillis()
                println("BST stress test (floor)")
                println("time (1000): ${end3 - end2} ms")
                println("***")

                for (i in 0..999) bst.rank("500")
                val end4 = System.currentTimeMillis()
                println("BST stress test (rank)")
                println("time (1000): ${end4 - end3} ms")
                println("***")

                for (i in 0..999) bst.select(500)
                val end5 = System.currentTimeMillis()
                println("BST stress test (select)")
                println("time (1000): ${end5 - end4} ms")
                println("***")

                for (i in 0..999) bst.keys()
                val end6 = System.currentTimeMillis()
                println("BST stress test (ordered iteration)")
                println("time (1000): ${end6 - end5} ms")
                println("***")
            }

            Then("keys() should produce a sorted array") {
                assertEquals(
                    bst.keys().windowed(2).any { (a, b) -> a <= b }, true
                )
            }
        }
    }
})