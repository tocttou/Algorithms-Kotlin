package lib.maxflowmincut

data class FlowEdge(private val v: Int, private val w: Int, private val capacity: Double) {
    private var flow = 0.0
    fun from() = v
    fun to() = w
    fun other(vertex: Int) =
        if (vertex == v) w else if (vertex == w) v else throw IllegalArgumentException()

    fun capacity() = capacity
    fun flow() = flow
    fun residualCapacityTo(vertex: Int): Double {
        return when (vertex) {
            v -> flow
            w -> capacity - flow
            else -> throw IllegalArgumentException()
        }
    }

    fun addResidualFlow(vertex: Int, delta: Double) {
        when (vertex) {
            v -> flow -= delta
            w -> flow += delta
            else -> throw IllegalArgumentException()
        }
    }

    override fun toString() = "<$v>-$flow/$capacity-<$w>"
}