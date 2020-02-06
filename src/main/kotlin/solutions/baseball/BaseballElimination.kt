package solutions.baseball

import edu.princeton.cs.algs4.FlowEdge
import edu.princeton.cs.algs4.FlowNetwork
import edu.princeton.cs.algs4.FordFulkerson
import edu.princeton.cs.algs4.In


class BaseballElimination(filename: String) {
    private lateinit var flowNetwork: FlowNetwork
    private var numteams = 0
    private val teamMap = HashMap<String, Array<Int>>()
    private val idToTeamMap = HashMap<Int, String>()
    private var eliminationCache = HashMap<String, HashSet<String>>()

    init {
        val `in` = In(filename)
        numteams = `in`.readInt()
        var counter = 0
        while (`in`.hasNextLine()) {
            val line = `in`.readLine()
            if (line != "") {
                val split = line.split(" ").filter { it != "" }
                val team = split[0]
                idToTeamMap[counter] = team
                teamMap[team] = arrayOf(counter) +
                        split.subList(1, split.size).map { it.toInt() }.toTypedArray()
                counter++
            }
        }
    }

    fun numberOfTeams() = numteams
    fun teams(): Iterable<String> = teamMap.keys
    fun wins(team: String) = teamMap[team]?.get(1) ?: throw IllegalArgumentException()
    fun losses(team: String) = teamMap[team]?.get(2) ?: throw IllegalArgumentException()
    fun remaining(team: String) = teamMap[team]?.get(3) ?: throw IllegalArgumentException()
    fun against(team1: String, team2: String): Int {
        val team2Index = teamMap[team2]?.get(0) ?: throw IllegalArgumentException()
        return teamMap[team1]?.get(4 + team2Index) ?: throw IllegalArgumentException()
    }

    fun isEliminated(team: String): Boolean {
        if (eliminationCache[team] != null) return true
        val teamToExclude = teamMap[team] ?: throw IllegalArgumentException()
        val otherTeams = teamMap.keys.filter { it != team }
        val otherTeamsIndexes = otherTeams.map { teamMap[it]!![0] }
        val totalMatchesInRemTeams = (numteams - 1) * (numteams - 2) / 2
        flowNetwork = FlowNetwork(1 + totalMatchesInRemTeams + numteams - 1 + 1)
        var counter = 1
        for (i in 0..(numteams - 2)) {
            val team1Index = otherTeamsIndexes[i]
            val team1 = idToTeamMap[team1Index]!!
            if (teamToExclude[1] + teamToExclude[3] - teamMap[team1]!![1] < 0) {
                if (eliminationCache[team] == null) {
                    eliminationCache[team] = hashSetOf(team1)
                }
                return true
            }
            for (j in (i + 1)..(numteams - 2)) {
                val team2Index = otherTeamsIndexes[j]
                flowNetwork.addEdge(
                    FlowEdge(0, counter, teamMap[team1]!![4 + team2Index].toDouble())
                )
                flowNetwork.addEdge(
                    FlowEdge(
                        counter,
                        totalMatchesInRemTeams + 1 + normalizedTeamIndex(team1Index, teamToExclude),
                        Double.POSITIVE_INFINITY
                    )
                )
                flowNetwork.addEdge(
                    FlowEdge(
                        counter,
                        totalMatchesInRemTeams + 1 + normalizedTeamIndex(team2Index, teamToExclude),
                        Double.POSITIVE_INFINITY
                    )
                )
                counter++
            }
            flowNetwork.addEdge(
                FlowEdge(
                    totalMatchesInRemTeams + 1 + (normalizedTeamIndex(team1Index, teamToExclude)),
                    totalMatchesInRemTeams + numteams - 1 + 1,
                    (teamToExclude[1] + teamToExclude[3] - teamMap[team1]!![1]).toDouble()
                )
            )
        }
        val fordFulkerson = FordFulkerson(flowNetwork, 0, totalMatchesInRemTeams + numteams - 1 + 1)
        var toReturn = false
        val eliminators = mutableListOf<String>()
        counter = 1
        for (i in 0..(numteams - 2)) {
            for (j in (i + 1)..(numteams - 2)) {
                if (fordFulkerson.inCut(counter)) {
                    toReturn = true
                    eliminators.add(idToTeamMap[otherTeamsIndexes[i]]!!)
                    eliminators.add(idToTeamMap[otherTeamsIndexes[j]]!!)
                }
                counter++
            }
        }
        if (toReturn) {
            if (eliminationCache[team] == null) {
                eliminationCache[team] = hashSetOf()
            }
            eliminationCache[team]!!.addAll(eliminators)
        }
        return toReturn
    }

    private fun normalizedTeamIndex(
        team1Index: Int, teamToExclude: Array<Int>
    ) = if (team1Index > teamToExclude[0]) team1Index - 1 else team1Index

    fun certificateOfElimination(team: String) =
        if (isEliminated(team)) eliminationCache[team]?.asIterable()
        else null
}