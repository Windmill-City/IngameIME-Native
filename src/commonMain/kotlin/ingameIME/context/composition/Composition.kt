package ingameIME.context.composition

import ingameIME.utils.Observable

/**
 * Composition - Manage state of [PreEdit] & [CandidateList]
 */
abstract class Composition(
    val preEdit: PreEdit,
) {
    /**
     * Changes by input method
     * Observable value
     * @see ingameIME.utils.setCallback
     */
    var candidates by Observable(CandidateList(emptyList()))
        protected set

    /**
     * If we have an active composition
     *
     * Changes by input method
     * Observable value
     * @see ingameIME.utils.setCallback
     */
    var composing: Boolean by Observable(false)
        protected set
}
