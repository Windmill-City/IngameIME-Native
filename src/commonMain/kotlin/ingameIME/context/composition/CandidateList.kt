package ingameIME.context.composition

import ingameIME.context.composition.CandidateList.Candidate

/**
 * Candidate List - Container of [Candidate]
 *
 * @param composition who it belongs to also act as a lock object
 * @param content inner data storage
 */
abstract class CandidateList(val composition: Composition, private val content: List<Candidate> = listOf()) :
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

    /**
     * Candidate item
     *
     * @param content candidate word
     */
    abstract class Candidate(val content: String)
}