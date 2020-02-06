package solutions.seam

import edu.princeton.cs.algs4.In
import edu.princeton.cs.algs4.Picture
import helpers.SCUtility
import lib.utils.ResourceUtils.resolve
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object SeamCarverTest : Spek({
    describe("a SeamCarver implemenation and a 10x10 image") {
        val filename = resolve("/seam/10x10.png")
        context("finding the energy matrix of this picture") {
            val sc = SeamCarver(Picture(filename))
            val tempMatrix = SCUtility.toEnergyMatrix(sc).map {
                it.toList().map {
                    "%.2f".format(it).toDouble()
                }.toTypedArray()
            }.toTypedArray()
            val tempMatrix2 = Array(10) { Array(10) { 0.0 } }
            for (i in 0..9) {
                for (j in 0..9) {
                    tempMatrix2[i][j] = tempMatrix[j][i]
                }
            }
            val energyMatrix = tempMatrix2.toList().map { it.toList() }
            val testVal = mutableListOf<MutableList<Double>>()
            val `in` = In(resolve("/seam/10x10.em.txt"))
            for (i in 0..9) {
                val temp = mutableListOf<Double>()
                for (j in 0..9) {
                    temp.add(`in`.readDouble())
                }
                testVal.add(temp)
            }
            it("should be equal to the test value") {
                assertEquals(testVal, energyMatrix)
            }
        }
        context("on finding the first vertical seam") {
            val sc = SeamCarver(Picture(filename))
            val verticalSeam = sc.findVerticalSeam().toList()
            val testVal = listOf(6, 7, 7, 7, 7, 7, 8, 8, 7, 6)
            it("should be equal to the test value") {
                assertEquals(testVal, verticalSeam)
            }
        }
        context("finding the vertical seam again after first vertical seam deletion") {
            val sc = SeamCarver(Picture(filename))
            sc.removeVerticalSeam(sc.findVerticalSeam())
            val verticalSeam = sc.findVerticalSeam().toList()
            val testVal = listOf(0, 1, 2, 3, 4, 4, 5, 5, 5, 4)
            it("should be equal to the test value") {
                assertEquals(testVal, verticalSeam)
            }
        }
        context("on finding the first horizontal seam") {
            val sc = SeamCarver(Picture(filename))
            val horizontalSeam = sc.findHorizontalSeam().toList()
            val testVal = listOf(0, 1, 2, 3, 3, 3, 3, 2, 1, 0)
            it("should be equal to the test value") {
                assertEquals(testVal, horizontalSeam)
            }
        }
        context("finding the horizontal seam again after first horizontal seam deletion") {
            val sc = SeamCarver(Picture(filename))
            sc.removeHorizontalSeam(sc.findHorizontalSeam())
            val horizontalSeam = sc.findHorizontalSeam().toList()
            val testVal = listOf(1, 2, 2, 3, 4, 5, 6, 7, 6, 5)
            it("should be equal to the test value") {
                assertEquals(testVal, horizontalSeam)
            }
        }
    }
    describe("a SeamCarver implementation and a 1x8 image") {
        val filename = resolve("/seam/1x8.png")
        context("on finding the first vertical seam") {
            val sc = SeamCarver(Picture(filename))
            val verticalSeam = sc.findVerticalSeam().toList()
            val testVal = listOf(0, 1, 2, 3, 4, 5, 6, 7)
            it("should be equal to the test value") {
                assertEquals(testVal, verticalSeam)
            }
        }
        context("on finding the first horizontal seam") {
            val sc = SeamCarver(Picture(filename))
            val verticalSeam = sc.findHorizontalSeam().toList()
            val testVal = listOf(0)
            it("should be equal to the test value") {
                assertEquals(testVal, verticalSeam)
            }
        }
    }
    describe("a SeamCarver implementation and a 8x1 image") {
        val filename = resolve("/seam/8x1.png")
        context("on finding the first vertical seam") {
            val sc = SeamCarver(Picture(filename))
            val verticalSeam = sc.findVerticalSeam().toList()
            val testVal = listOf(0)
            it("should be equal to the test value") {
                assertEquals(testVal, verticalSeam)
            }
        }
        context("on finding the first horizontal seam") {
            val sc = SeamCarver(Picture(filename))
            val verticalSeam = sc.findHorizontalSeam().toList()
            val testVal = listOf(0, 1, 2, 3, 4, 5, 6, 7)
            it("should be equal to the test value") {
                assertEquals(testVal, verticalSeam)
            }
        }
    }
    describe("a SeamCarver implementation and a 1x1 image") {
        val filename = resolve("/seam/1x1.png")
        context("on finding the first vertical seam") {
            val sc = SeamCarver(Picture(filename))
            val verticalSeam = sc.findVerticalSeam().toList()
            val testVal = listOf(0)
            it("should be equal to the test value") {
                assertEquals(testVal, verticalSeam)
            }
        }
        context("on finding the first horizontal seam") {
            val sc = SeamCarver(Picture(filename))
            val verticalSeam = sc.findHorizontalSeam().toList()
            val testVal = listOf(0)
            it("should be equal to the test value") {
                assertEquals(testVal, verticalSeam)
            }
        }
    }
})