package lib.shortestpath.applications

import edu.princeton.cs.algs4.In
import lib.shortestpath.AcyclicSP
import lib.shortestpath.DirectedEdge
import lib.shortestpath.EdgeWeightedDigraph

class ParallelJobScheduling(filename: String) {
    private val edgeWeightedDigraph: EdgeWeightedDigraph
    private val acyclicSP: AcyclicSP

    init {
        val `in` = In(filename)
        val numJobs = `in`.readInt()
        edgeWeightedDigraph = EdgeWeightedDigraph(2 * numJobs + 2)
        for (jobNumber in 0..numJobs) {
            val s = `in`.readLine()
            if (s.trim() != "") {
                val split = s.split(" ")
                val node = split[0].toInt()
                val weight = split[1].toDouble()
                val dependents = split.subList(2, split.size)
                edgeWeightedDigraph.addEdge(
                    DirectedEdge(jobToIndex(node), jobToIndex(node) + 1, -1 * weight)
                )
                edgeWeightedDigraph.addEdge(DirectedEdge(0, jobToIndex(node), 0.0))
                edgeWeightedDigraph.addEdge(
                    DirectedEdge(
                        jobToIndex(node) + 1, 2 * numJobs + 1, 0.0
                    )
                )
                for (dependent in dependents) {
                    edgeWeightedDigraph.addEdge(
                        DirectedEdge(jobToIndex(node) + 1, jobToIndex(dependent.toInt()), 0.0)
                    )
                }
            }
        }
        acyclicSP = AcyclicSP(edgeWeightedDigraph, 0)
    }

    fun scheduleTime(job: Int) = -1 * acyclicSP.distTo(jobToIndex(job))

    private fun jobToIndex(job: Int) = 2 * job + 1
}