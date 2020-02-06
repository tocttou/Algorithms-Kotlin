package lib.shortestpath

data class DirectedEdge(private val v: Int, private val w: Int, private val weight: Double) {
    fun from() = v
    fun to() = w
    fun weight() = weight
    override fun toString() = "<$v>-$weight-<$w>"
}