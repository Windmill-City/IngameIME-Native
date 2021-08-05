package ingameIME.context.composition

typealias Candidate = String

/**
 * Candidate List - Container of [Candidate]
 *
 * @param content inner data storage
 */
data class CandidateList(private val content: List<Candidate> = listOf()) :
    List<Candidate> by content {

    /**
     * Range of [Candidate] that we need to draw
     *
     * As there maybe thousands of [Candidate]s in the [content] list, we just need to draw part of them
     * This range is decided by input method, and we cant move this range freely
     */
    val displayRange: IntRange = IntRange.EMPTY

    /**
     * Index of [Candidate] in the [content] list
     */
    fun Candidate.getIndex() = this@CandidateList.content.indexOf(this)

    /**
     * Index of [Candidate] on screen, start from 1
     */
    fun Candidate.getDisplayIndex() = this.getIndex() - displayRange.first + 1
}