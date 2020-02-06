package solutions.wordnet

import edu.princeton.cs.algs4.Digraph
import edu.princeton.cs.algs4.In
import edu.princeton.cs.algs4.Stack

class WordNet(synsets: String, hypernyms: String) {
    private val digraph: Digraph
    private val wordmap = HashMap<String, MutableList<Int>>()
    private val idmap = HashMap<Int, List<String>>()
    private val sap: SAP
    private var hasCycle = false

    init {
        val synsetsIn = In(synsets)
        var counter = 0
        while (synsetsIn.hasNextLine()) {
            val nextLine = synsetsIn.readLine()
            if (nextLine.trim() != "") {
                val split = nextLine.split(",")
                val id = split[0].toInt()
                val nouns = split[1].split(" ")
                for (noun in nouns) {
                    val idList = wordmap[noun]
                    if (idList == null) wordmap[noun] = mutableListOf(id)
                    else idList.add(id)
                }
                idmap[id] = nouns
            }
            counter++
        }
        synsetsIn.close()
        digraph = Digraph(counter)
        val hypernymsIn = In(hypernyms)
        while (hypernymsIn.hasNextLine()) {
            val nextLine = hypernymsIn.readLine()
            if (nextLine.trim() != "") {
                val split = nextLine.split(",")
                val id = split[0].toInt()
                if (split.size > 1) {
                    for (adj in split.subList(1, split.size)) digraph.addEdge(id, adj.toInt())
                }
            }
        }
        hypernymsIn.close()
        if (!isRootedDAG(digraph)) throw IllegalArgumentException("digraph should be a rooted DAG")
        sap = SAP(digraph)
    }

    private fun isRootedDAG(digraph: Digraph): Boolean {
        val rootCandidates = (0..(digraph.V() - 1)).filter { digraph.outdegree(it) == 0 }
        if (rootCandidates.size != 1) return false
        val reverseDigraph = digraph.reverse()
        val marked = Array(digraph.V()) { false }
        dfs(marked, reverseDigraph, rootCandidates[0], Stack())
        if (hasCycle) return false
        val unmarkedNode = marked.firstOrNull { !it }
        return unmarkedNode == null
    }

    private fun dfs(
        marked: Array<Boolean>, digraph: Digraph, initNode: Int, dfsStack: Stack<Int>
    ) {
        if (hasCycle) return
        marked[initNode] = true
        dfsStack.push(initNode)
        for (adj in digraph.adj(initNode)) {
            if (!marked[adj]) {
                dfs(marked, digraph, adj, dfsStack)
            } else {
                if (dfsStack.contains(adj)) {
                    hasCycle = true
                    break
                }
            }
        }
        dfsStack.pop()
    }

    fun nouns(): Iterable<String> = wordmap.keys

    fun isNoun(word: String) = wordmap.containsKey(word)

    fun sap(nounA: String, nounB: String): String {
        val idA = wordmap[nounA]
        val idB = wordmap[nounB]
        if (idA == null || idB == null) throw IllegalArgumentException("nouns not present in any synset")
        return idmap[sap.ancestor(idA, idB)]!!.joinToString(" ")
    }

    fun distance(nounA: String, nounB: String): Int {
        val idA = wordmap[nounA]
        val idB = wordmap[nounB]
        if (idA == null || idB == null) throw IllegalArgumentException("nouns not present in any synset")
        return sap.length(idA, idB)
    }
}