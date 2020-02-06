package lib.elementarysymboltables

import edu.princeton.cs.algs4.StdRandom
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

object SequentialSearchSTTest : Spek({
    Feature("1. a sequential search symbol table") {
        val searchST by memoized { SequentialSearchST<String, Int>() }
        Scenario("putting some values") {
            When("setup") {
                searchST.put("hello", 1)
                searchST.put("hello", 2)
                searchST.put("world", 3)
                searchST.put("hello", 4)
                searchST.put("hey", 5)
            }

            Then("should be equal to the test values") {
                assertEquals(searchST.keys(), listOf("hello", "hey", "world"))
                assertEquals(searchST.keys("hez", "world"), listOf("world"))
                assertEquals(searchST.get("hello"), 4)
                assertEquals(searchST.get("hell"), null)
                assertEquals(searchST.contains("hey"), true)
                assertEquals(searchST.contains("he"), false)
                assertEquals(searchST.isEmpty(), false)
                assertEquals(searchST.size(), 3)
                assertEquals(searchST.size("hey", "world"), 2)
                assertEquals(searchST.size("hey", "worle"), 2)
                assertEquals(searchST.size("her", "worle"), 2)
                assertEquals(searchST.size("hed", "worle"), 3)
                assertFailsWith<IllegalArgumentException> { searchST.size("world", "hey") }
                assertEquals(searchST.min(), "hello")
                assertEquals(searchST.max(), "world")
                assertEquals(searchST.floor("world"), "world")
                assertEquals(searchST.floor("worle"), "world")
                assertEquals(searchST.floor("worl"), "hey")
                assertEquals(searchST.ceiling("hey"), "hey")
                assertEquals(searchST.ceiling("hez"), "world")
                assertEquals(searchST.rank("world"), 2)
                assertEquals(searchST.rank("hello"), 0)
                assertEquals(searchST.select(2), "world")
                assertEquals(searchST.select(1), "hey")
                assertEquals(searchST.select(0), "hello")
            }
        }
        Scenario("deleting some values") {
            When("setup") {
                searchST.put("hello", 1)
                searchST.put("world", 2)
                searchST.put("hey", 3)
                searchST.deleteMax()
                searchST.deleteMin()
            }

            Then("should be equal to the test values") {
                assertEquals(searchST.keys(), listOf("hey"))
                assertEquals(searchST.keys("hez", "world"), listOf())
                assertEquals(searchST.get("hello"), null)
                assertEquals(searchST.contains("hey"), true)
                assertEquals(searchST.contains("world"), false)
                assertEquals(searchST.isEmpty(), false)
                assertEquals(searchST.size(), 1)
                assertEquals(searchST.size("hey", "world"), 1)
                assertEquals(searchST.size("hez", "world"), 0)
                assertEquals(searchST.size("her", "worle"), 1)
                assertEquals(searchST.min(), "hey")
                assertEquals(searchST.max(), "hey")
                assertEquals(searchST.floor("world"), "hey")
                assertEquals(searchST.floor("worle"), "hey")
                assertEquals(searchST.ceiling("hey"), "hey")
                assertEquals(searchST.ceiling("hez"), null)
                assertEquals(searchST.rank("world"), 1)
                assertEquals(searchST.rank("hey"), 0)
                assertFailsWith<IllegalArgumentException> { searchST.select(2) }
                assertEquals(searchST.select(0), "hey")
            }
        }
    }
    Feature("2. a sequential search symbol table") {
        val searchST by memoized { SequentialSearchST<String, Int>() }
        val randomNumbers by memoized { Array(1000) { StdRandom.uniform(1000) } }
        Scenario("stress testing it") {
            When("stress test") {
                val start = System.currentTimeMillis()

                for (i in 0..999) searchST.put(randomNumbers[i].toString(), randomNumbers[i])
                val end1 = System.currentTimeMillis()
                println("SeqSearchST stress test (put)")
                println("time (1000): ${end1 - start} ms")
                println("***")

                for (i in 0..999) searchST.max()
                val end2 = System.currentTimeMillis()
                println("SeqSearchST stress test (max)")
                println("time (1000): ${end2 - end1} ms")
                println("***")

                for (i in 0..999) searchST.floor("500")
                val end3 = System.currentTimeMillis()
                println("SeqSearchST stress test (floor)")
                println("time (1000): ${end3 - end2} ms")
                println("***")

                for (i in 0..999) searchST.rank("500")
                val end4 = System.currentTimeMillis()
                println("SeqSearchST stress test (rank)")
                println("time (1000): ${end4 - end3} ms")
                println("***")

                for (i in 0..999) searchST.select(500)
                val end5 = System.currentTimeMillis()
                println("SeqSearchST stress test (select)")
                println("time (1000): ${end5 - end4} ms")
                println("***")

                for (i in 0..999) searchST.keys()
                val end6 = System.currentTimeMillis()
                println("SeqSearchST stress test (ordered iteration)")
                println("time (1000): ${end6 - end5} ms")
                println("***")
            }

            Then("keys() should produce a sorted array") {
                assertEquals(
                    searchST.keys().windowed(2).any { (a, b) -> a <= b }, true
                )
            }
        }
    }
})