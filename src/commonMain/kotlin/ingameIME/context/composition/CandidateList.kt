package ingameIME.context.composition

typealias Candidate = String

/**
 * Candidate List - Container of [Candidate]s
 */
abstract class CandidateList {
    data class Context(
        /**
         * [Candidate]s, may contain more data than display range
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
         *
         * The [Candidate] that will be [Commit]ed when user press space key.
         * input method will send commit event if needed, so we don't need to handling commit here
         * use the value here for some visual effect that telling user what [Candidate] they're selecting
         *
         * @see getIndex
         */
        val curSel: Int
    ) {
        /**
         * If it has empty [content]
         */
        fun isEmpty(): Boolean = content.isEmpty()

        /**
         * If [content] not empty
         */
        fun isNotEmpty(): Boolean = content.isNotEmpty()
    }

    /**
     * Changes by input method
     */
    var context: Context = Context(emptyList(), IntRange.EMPTY, -1)
        protected set

    /**
     * Select another [Candidate] by its index
     *
     * We need to call [setSelection] when user selects [Candidate]s by hovering the mouse
     * if we do the rendering job
     *
     * @param index of [Candidate] to select
     * @see getIndex to get the index
     *
     * @Note should in the range of [Context.displayRange]
     */
    abstract fun setSelection(index: Int)

    /**
     * [Commit] specific [Candidate] by its index
     *
     * We need to call [setFinalize] when user selects [Candidate]s by clicking the mouse
     * if we do the rendering job
     *
     * @param index of [Candidate] to [Commit]
     * @see getIndex to get the index
     *
     * @Note should in the range of [Context.displayRange]
     */
    abstract fun setFinalize(index: Int)

    /**
     * Index of [Candidate] for selecting
     */
    open fun Context.getIndex(candidate: Candidate) = this.content.indexOf(candidate)

    /**
     * Index of [Candidate] for display, start from 1
     */
    open fun Context.getDisplayIndex(candidate: Candidate) = this.getIndex(candidate) - this.displayRange.first + 1
}