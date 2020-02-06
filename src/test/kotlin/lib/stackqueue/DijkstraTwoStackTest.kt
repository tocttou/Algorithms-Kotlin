package lib.stackqueue

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object DijkstraTwoStackTest : Spek({
    describe("an arithmetic expression") {
        val expression = "( 1 + ( ( 2 + 3 ) * ( 4 * 5 ) ) )"
        context("evaluating it") {
            val dij = DijkstraTwoStack()
            val eval = dij.eval(expression)
            it("should be equal to the test value") {
                val testVal = 101
                assertEquals(eval, testVal)
            }
        }
    }
    describe("a postfix arithmetic expression") {
        val expression = "123+45**+"
        context("evaluating it") {
            val dij = DijkstraTwoStack()
            val eval = dij.evalPostfix(expression)
            it("should be equal to the test value") {
                val testVal = 101
                assertEquals(eval, testVal)
            }
        }
    }
})