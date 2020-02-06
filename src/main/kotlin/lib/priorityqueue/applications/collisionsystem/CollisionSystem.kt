package lib.priorityqueue.applications.collisionsystem

import edu.princeton.cs.algs4.Particle
import edu.princeton.cs.algs4.StdDraw
import edu.princeton.cs.algs4.StdIn
import lib.priorityqueue.MinPQ
import java.awt.Color

class CollisionSystem(p: Array<Particle>) {
    private val redrawsPerTick = 0.5
    private lateinit var pq: MinPQ<Event>
    private var t = 0.0
    private val particles = p.clone()

    private fun predict(a: Particle?, limit: Double) {
        if (a == null) return

        for (particle in particles) {
            val dt = a.timeToHit(particle)
            if (t + dt <= limit) pq.insert(Event(t + dt, a, particle))
        }

        val dtX = a.timeToHitVerticalWall()
        val dtY = a.timeToHitHorizontalWall()
        if (t + dtX <= limit) pq.insert(Event(t + dtX, a, null))
        if (t + dtY <= limit) pq.insert(Event(t + dtY, null, a))
    }

    private fun redraw(limit: Double) {
        StdDraw.clear()
        for (particle in particles) particle.draw()
        StdDraw.show()
        StdDraw.pause(20)
        if (t < limit) pq.insert(Event(t + 1.0 / redrawsPerTick, null, null))
    }

    fun simulate(limit: Double) {
        pq = MinPQ()
        for (i in particles.indices) predict(particles[i], limit)
        pq.insert(Event(0.0, null, null))

        while (!pq.isEmpty()) {
            val e = pq.delMin()
            if (!e.isValid()) continue
            val a = e.a
            val b = e.b

            for (particle in particles) particle.move(e.time - t)
            t = e.time

            when {
                a != null && b != null -> a.bounceOff(b)
                a != null && b == null -> a.bounceOffVerticalWall()
                a == null && b != null -> b.bounceOffHorizontalWall()
                a == null && b == null -> redraw(limit)
            }

            predict(a, limit)
            predict(b, limit)
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            StdDraw.enableDoubleBuffering()
            val particles = if (args.size == 1) {
                val n = Integer.parseInt(args[0])
                Array(n) { Particle() }
            } else {
                val n = StdIn.readInt()
                Array(n) {
                    val rx = StdIn.readDouble()
                    val ry = StdIn.readDouble()
                    val vx = StdIn.readDouble()
                    val vy = StdIn.readDouble()
                    val radius = StdIn.readDouble()
                    val mass = StdIn.readDouble()
                    val r = StdIn.readInt()
                    val g = StdIn.readInt()
                    val b = StdIn.readInt()
                    val color = Color(r, g, b)
                    Particle(rx, ry, vx, vy, radius, mass, color)
                }
            }

            CollisionSystem(particles).simulate(10000.0)
        }
    }
}