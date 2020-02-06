package solutions.queues

import edu.princeton.cs.algs4.StdIn
import edu.princeton.cs.algs4.StdOut

class Permutation {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val n = args[0].toInt()
            val queue = RandomizedQueue<String>()
            while (!StdIn.isEmpty()) {
                queue.enqueue(StdIn.readString())
            }
            for ((index, i) in queue.withIndex()) {
                if (index > n - 1) break
                else StdOut.println(i)
            }
        }
    }
}