package solutions.wordnet

import edu.princeton.cs.algs4.Digraph
import edu.princeton.cs.algs4.In
import lib.utils.ResourceUtils.resolve
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

object SAPTest : Spek({
    describe("1. a SAP implementation and a digraph") {
        val filename = resolve("/wordnet/digraph1.txt")
        val `in` = In(filename)
        val digraph = Digraph(`in`)
        val sap = SAP(digraph)
        context("finding the lowest common ancestor for 3 and 11") {
            val ancestor = sap.ancestor(3, 11)
            it("should be equal to 1") {
                assertEquals(1, ancestor)
            }
        }
        context("finding the length between 3 and 11") {
            val length = sap.length(3, 11)
            it("should be equal to 4") {
                assertEquals(4, length)
            }
        }
        context("finding the LCA for (7, 3, 8) and (10, 11, 12)") {
            val ancestor = sap.ancestor(listOf(7, 3, 8), listOf(10, 11, 12))
            it("should be equal to 1") {
                assertEquals(1, ancestor)
            }
        }
        context("finding the length between (7, 3, 8) and (10, 11, 12)") {
            val length = sap.length(listOf(7, 3, 8), listOf(10, 11, 12))
            it("should be equal to 3") {
                assertEquals(3, length)
            }
        }
        context("finding the LCA for (7, 3, 8) and (1, 4, 5)") {
            val ancestor = sap.ancestor(listOf(7, 3, 8), listOf(1, 4, 5))
            it("should be equal to 1") {
                assertEquals(1, ancestor)
            }
        }
        context("finding the length between (7, 3, 8) and (1, 4, 5)") {
            val length = sap.length(listOf(7, 3, 8), listOf(1, 4, 5))
            it("should be equal to 1") {
                assertEquals(1, length)
            }
        }
        context("passing ancestor() out of bound values") {
            it("should fail with IllegalArgumentsException") {
                assertFailsWith<IllegalArgumentException> { sap.ancestor(200, 300) }
            }
        }
    }
    describe("2. a SAP implementation and a digraph") {
        val filename = resolve("/wordnet/digraph2.txt")
        val `in` = In(filename)
        val digraph = Digraph(`in`)
        val sap = SAP(digraph)
        context("finding the lowest common ancestor for 1 and 5") {
            val ancestor = sap.ancestor(1, 5)
            it("should be equal to 0") {
                assertEquals(0, ancestor)
            }
        }
        context("finding the length between 1 and 5") {
            val length = sap.length(1, 5)
            it("should be equal to 2") {
                assertEquals(2, length)
            }
        }
        context("finding the lowest common ancestor for 1 and 3") {
            val ancestor = sap.ancestor(1, 3)
            it("should be equal to 3") {
                assertEquals(3, ancestor)
            }
        }
    }
    describe("3. a SAP implementation and a digraph") {
        val filename = resolve("/wordnet/digraph3.txt")
        val `in` = In(filename)
        val digraph = Digraph(`in`)
        val sap = SAP(digraph)
        context("finding the lowest common ancestor for 1 and 5") {
            val ancestor = sap.ancestor(1, 5)
            it("should be equal to 5") {
                assertEquals(1, ancestor)
            }
        }
        context("finding the length between 1 and 5") {
            val length = sap.length(1, 5)
            it("should be equal to 3") {
                assertEquals(2, length)
            }
        }
        context("finding the lowest common ancestor for 1 and 3") {
            val ancestor = sap.ancestor(1, 3)
            it("should be equal to 3") {
                assertEquals(3, ancestor)
            }
        }
        context("finding the lowest common ancestor for 1 and 13") {
            val ancestor = sap.ancestor(1, 13)
            it("should be equal to -1") {
                assertEquals(-1, ancestor)
            }
        }
        context("finding the distance between 13 and 8") {
            val ancestor = sap.length(13, 8)
            it("should be equal to 5") {
                assertEquals(5, ancestor)
            }
        }
    }
})