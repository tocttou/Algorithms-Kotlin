package solutions.eightpuzzle

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object SolverTest : Spek({
    describe("1. an initial 3x3 matrix") {
        val initialMatrix = arrayOf(
            arrayOf(0, 1, 3), arrayOf(4, 2, 5), arrayOf(7, 8, 6)
        )
        val initialBoard = Board(initialMatrix)
        context("running 8puzzle solver on it") {
            val solver = Solver(initialBoard)
            it("should produce the following results") {
                assertEquals(solver.isSolvable(), true)
                assertEquals(solver.moves(), 4)
                val testSol = listOf<Board>(
                    Board(
                        arrayOf(
                            arrayOf(0, 1, 3), arrayOf(4, 2, 5), arrayOf(7, 8, 6)
                        )
                    ), Board(
                        arrayOf(
                            arrayOf(1, 0, 3), arrayOf(4, 2, 5), arrayOf(7, 8, 6)
                        )
                    ), Board(
                        arrayOf(
                            arrayOf(1, 2, 3), arrayOf(4, 0, 5), arrayOf(7, 8, 6)
                        )
                    ), Board(
                        arrayOf(
                            arrayOf(1, 2, 3), arrayOf(4, 5, 0), arrayOf(7, 8, 6)
                        )
                    ), Board(
                        arrayOf(
                            arrayOf(1, 2, 3), arrayOf(4, 5, 6), arrayOf(7, 8, 0)
                        )
                    )
                )
                assertEquals(solver.solution()?.toList(), testSol)
            }
        }
    }
    describe("2. an initial 3x3 matrix") {
        val initialMatrix = arrayOf(
            arrayOf(1, 2, 3), arrayOf(4, 5, 6), arrayOf(8, 7, 0)
        )
        val initialBoard = Board(initialMatrix)
        context("running 8puzzle solver on it") {
            val solver = Solver(initialBoard)
            it("should produce the following results") {
                assertEquals(solver.isSolvable(), false)
                assertEquals(solver.moves(), -1)
                val testSol = null
                assertEquals(solver.solution()?.toList(), testSol)
            }
        }
    }
    describe("3. an initial 3x3 matrix") {
        val initialMatrix = arrayOf(
            arrayOf(1, 2, 3), arrayOf(4, 5, 6), arrayOf(7, 8, 0)
        )
        val initialBoard = Board(initialMatrix)
        context("running 8puzzle solver on it") {
            val solver = Solver(initialBoard)
            it("should produce the following results") {
                assertEquals(solver.isSolvable(), true)
                assertEquals(solver.moves(), 0)
                val testSol = listOf<Board>(Board(initialMatrix))
                assertEquals(solver.solution()?.toList(), testSol)
            }
        }
    }

})