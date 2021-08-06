package ingameIME.context.composition

import ingameIME.utils.Observable

typealias Candidate = String

/**
 * Candidate List - Container of [Candidate]
 */
abstract class CandidateList {
    data class Context(
        /**
         * Candidate data, may contain more data than display range
         */
        val content: List<Candidate>,

        /**
         * Range of [Candidate] that we need to draw
         *
         * The range here describes active page of input method, we need to draw all of them,
         * as we can't decide where the page start or end
         * @Note Both start and end are inclusive
         */
        val displayRange: IntRange,

        /**
         * Current selected [Candidate]'s index
         * @see getIndex
         */
        val curSel: Int
    )

    /**
     * Changes by input method
     * Observable value
     * @see ingameIME.utils.setCallback
     */
    var context: Context by Observable(Context(emptyList(), IntRange.EMPTY, -1))
        protected set

    /**
     * Select another [Candidate] by assign a new index
     *
     * @param index of [Candidate] to select
     * @see getIndex to get the index
     *
     * @Note should in the range of [Context.displayRange]
     */
    abstract fun setSelection(index: Int)

    /**
     * Index of [Candidate] for selecting
     */
    open fun Context.getIndex(candidate: Candidate) = this.content.indexOf(candidate)

    /**
     * Index of [Candidate] for display, start from 1
     */
    open fun Context.getDisplayIndex(candidate: Candidate) = this.getIndex(candidate) - this.displayRange.first + 1
}