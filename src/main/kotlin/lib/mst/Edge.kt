package lib.mst

data class Edge(private val v: Int, private val w: Int, private val weight: Double) :
    Comparable<Edge> {
    fun either() = v
    fun other(node: Int): Int = if (node == v) w else v
    override fun compareTo(other: Edge) = compareValues(this.weight, other.weight)
    fun weight() = weight
    override fun toString() = "<$v>-$weight-<$w>"
}