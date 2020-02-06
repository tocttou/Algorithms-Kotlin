package lib.geometricapplicationsbst

import lib.utils.ResourceUtils.resolve
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.io.BufferedReader
import java.io.File
import kotlin.test.assertEquals

object RectangleIntersectionTest : Spek({
    fun parseLine(bufferedReader: BufferedReader): Array<Rectangle> {
        val numPoints = bufferedReader.readLine().trim().toInt()
        return Array(numPoints) {
            val flatCoords = bufferedReader.readLine().trim().split(" ").map { it.toInt() }
            val lls = Pair(flatCoords[0], Interval1DST.Interval(flatCoords[1], flatCoords[3]))
            val rls = Pair(flatCoords[4], Interval1DST.Interval(flatCoords[5], flatCoords[7]))
            arrayOf(lls, rls)
        }
    }

    describe("1. a number of horizontal and vertical rectangles") {
        val inputStream =
            File(resolve("/geometricapplicationsbst/rectinput/input1.txt")).inputStream()
        val bufferedReader = inputStream.bufferedReader()
        val hr = parseLine(bufferedReader)
        val vr = parseLine(bufferedReader)
        bufferedReader.close()
        context("finding the number of intersections") {
            val li = RectangleIntersection(hr, vr)
            it("should be equal to the test value") {
                assertEquals(2, li.intersect())
            }
        }
    }

    describe("2. a number of horizontal and vertical rectangles") {
        val inputStream =
            File(resolve("/geometricapplicationsbst/rectinput/input2.txt")).inputStream()
        val bufferedReader = inputStream.bufferedReader()
        val hr = parseLine(bufferedReader)
        val vr = parseLine(bufferedReader)
        bufferedReader.close()
        context("finding the number of intersections") {
            val li = RectangleIntersection(hr, vr)
            it("should be equal to the test value") {
                assertEquals(1, li.intersect())
            }
        }
    }

    describe("3. a number of horizontal and vertical rectangles") {
        val inputStream =
            File(resolve("/geometricapplicationsbst/rectinput/input3.txt")).inputStream()
        val bufferedReader = inputStream.bufferedReader()
        val hr = parseLine(bufferedReader)
        val vr = parseLine(bufferedReader)
        bufferedReader.close()
        context("finding the number of intersections") {
            val li = RectangleIntersection(hr, vr)
            it("should be equal to the test value") {
                assertEquals(3, li.intersect())
            }
        }
    }
})