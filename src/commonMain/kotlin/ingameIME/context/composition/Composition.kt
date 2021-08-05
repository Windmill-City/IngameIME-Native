package ingameIME.context.composition

import ingameIME.context.IInputContext
import ingameIME.utils.Observable

/**
 * Composition - Manage state of [PreEdit] & [CandidateList]
 *
 * @param context who it belongs to
 *
 * @Note Lock this object before access to any fields in it
 */
abstract class Composition(
    val context: IInputContext,
    preEdit: PreEdit,
    candidates: CandidateList
) {
    /**
     * Observable value
     * @see ingameIME.utils.setCallback
     */
    val preEdit by Observable(preEdit)

    /**
     * Observable value
     * @see ingameIME.utils.setCallback
     */
    val candidates by Observable(candidates)

    /**
     * If we have an active composition
     *
     * Observable value
     * @see ingameIME.utils.setCallback
     */
    val composing: Boolean by Observable(false)
}
