package ingameIME.context.composition

import ingameIME.utils.Observable

/**
 * Composition - Manage state of [PreEdit] & [CandidateList]
 */
abstract class Composition(
    val preEdit: PreEdit,
    val candidates: CandidateList,
) {

    /**
     * If we have an active composition
     *
     * Changes by input method
     * Observable value
     * @see ingameIME.utils.setCallback
     */
    var composing: Boolean by Observable(false)
        protected set

    /**
     * Terminate active composition
     */
    abstract fun terminate()
}
