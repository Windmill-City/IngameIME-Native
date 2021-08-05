package ingameIME.context.composition

import ingameIME.context.IInputContext
import ingameIME.utils.Observable

/**
 * Composition - Manage state of [PreEdit] & [CandidateList]
 *
 * @param context who it belongs to
 */
abstract class Composition(
    val context: IInputContext,
    val preEdit: PreEdit,
) {
    /**
     * Changes by input method
     * Observable value
     * @see ingameIME.utils.setCallback
     */
    @Suppress("LeakingThis")
    val candidates by Observable(CandidateList(this, emptyList()))

    /**
     * If we have an active composition
     *
     * Changes by input method
     * Observable value
     * @see ingameIME.utils.setCallback
     */
    val composing: Boolean by Observable(false)
}
