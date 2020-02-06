package lib.geometricapplicationsbst

import lib.utils.ResourceUtils.resolve
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import src.geometricapplicationsbst.LineIntersection
import java.io.BufferedReader
import java.io.File
import kotlin.test.assertEquals

object LineIntersectionTest : Spek({
    fun parseLine(bufferedReader: BufferedReader): Array<Array<Pair<Int, Int>>> {
        val numPoints = bufferedReader.readLine().trim().toInt()
        return Array(numPoints) {
            val flatCoords = bufferedReader.readLine().trim().split(" ").map { it.toInt() }
            val lp = Pair(flatCoords[0], flatCoords[1])
            val rp = Pair(flatCoords[2], flatCoords[3])
            arrayOf(lp, rp)
        }
    }

    describe("1. a number of horizontal and vertical line segments") {
        val inputStream =
            File(resolve("/geometricapplicationsbst/lineinput/input1.txt")).inputStream()
        val bufferedReader = inputStream.bufferedReader()
        val hs = parseLine(bufferedReader)
        val vs = parseLine(bufferedReader)
        bufferedReader.close()
        context("finding the number of intersections") {
            val li = LineIntersection(hs, vs)
            it("should be equal to the test value") {
                assertEquals(3, li.intersect())
            }
        }
    }

    describe("2. a number of horizontal and vertical line segments") {
        val inputStream =
            File(resolve("/geometricapplicationsbst/lineinput/input2.txt")).inputStream()
        val bufferedReader = inputStream.bufferedReader()
        val hs = parseLine(bufferedReader)
        val vs = parseLine(bufferedReader)
        bufferedReader.close()
        context("finding the number of intersections") {
            val li = LineIntersection(hs, vs)
            it("should be equal to the test value") {
                assertEquals(1, li.intersect())
            }
        }
    }

    describe("3. a number of horizontal and vertical line segments") {
        val inputStream =
            File(resolve("/geometricapplicationsbst/lineinput/input3.txt")).inputStream()
        val bufferedReader = inputStream.bufferedReader()
        val hs = parseLine(bufferedReader)
        val vs = parseLine(bufferedReader)
        bufferedReader.close()
        context("finding the number of intersections") {
            val li = LineIntersection(hs, vs)
            it("should be equal to the test value") {
                assertEquals(0, li.intersect())
            }
        }
    }
})