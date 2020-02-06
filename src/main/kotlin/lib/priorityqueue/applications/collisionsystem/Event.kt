package lib.priorityqueue.applications.collisionsystem

import edu.princeton.cs.algs4.Particle

class Event(val time: Double, val a: Particle?, val b: Particle?) : Comparable<Event> {
    private val countA: Int = a?.count() ?: -1
    private val countB: Int = b?.count() ?: -1

    fun isValid(): Boolean {
        if (a != null && a.count() != countA) return false
        return !(b != null && b.count() != countB)
    }

    override fun compareTo(other: Event): Int {
        return java.lang.Double.compare(this.time, other.time)
    }
}

