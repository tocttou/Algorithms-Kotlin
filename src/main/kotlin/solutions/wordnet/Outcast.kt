package solutions.wordnet


class Outcast(private val wordnet: WordNet) {
    fun outcast(nouns: Array<String>): String {
        var maxDistanceIndex = 0
        var maxDistanceYet = 0
        for (noun in nouns.withIndex()) {
            val distance = nouns.asSequence().filter { it != noun.value }.fold(0) { acc, s ->
                acc + wordnet.distance(noun.value, s)
            }
            if (maxDistanceYet < distance) {
                maxDistanceIndex = noun.index
                maxDistanceYet = distance
            }
        }
        return nouns[maxDistanceIndex]
    }
}