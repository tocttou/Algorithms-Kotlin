package lib.shortestpath.applications

import lib.utils.ResourceUtils.resolve
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object ParallelJobSchedulingTest : Spek({
    describe("a ParallelJobSchedulin implementation and a job list") {
        // job list format: <job-number> <duration> <list-of-dependent-jobs>
        val filename = resolve("/shortestpath/jobdag.txt")
        val parallelJobScheduling = ParallelJobScheduling(filename)
        context("calculating the scheduled time for job=1") {
            it("should be equal to 41.0") {
                assertEquals(41.0, parallelJobScheduling.scheduleTime(1))
            }
        }
        context("calculating the scheduled time for job=6") {
            it("should be equal to 70.0") {
                assertEquals(70.0, parallelJobScheduling.scheduleTime(6))
            }
        }
        context("calculating the scheduled time for job=2") {
            it("should be equal to 123.0") {
                assertEquals(123.0, parallelJobScheduling.scheduleTime(2))
            }
        }
    }
})