package lib.geometricapplicationsbst

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object Interval1DSTTest : Spek({
    describe("an initialized interval dst") {
        val intervalST = Interval1DST<Int>()
        intervalST.put(17, 19)
        intervalST.put(5, 8)
        intervalST.put(15, 18)
        intervalST.put(21, 24)
        intervalST.delete(21, 24)
        intervalST.put(7, 100)
        context("finding intersection with range [16, 17]") {
            val intersection = intervalST.intersects(16, 17).toList()
            it("should be equal to the test value") {
                assertEquals(
                    listOf(
                        Interval1DST.Interval(17, 19),
                        Interval1DST.Interval(15, 18),
                        Interval1DST.Interval(7, 100)
                    ), intersection
                )
            }
        }
        context("finding intersection with range [101, 123]") {
            val intersection = intervalST.intersects(101, 123).toList()
            it("should be equal to the test value") {
                assertEquals(listOf(), intersection)
            }
        }
        context("finding intersection with range [22, 23]") {
            val intersection = intervalST.intersects(22, 23).toList()
            it("should be equal to the test value") {
                assertEquals(listOf(Interval1DST.Interval(7, 100)), intersection)
            }
        }
    }
})