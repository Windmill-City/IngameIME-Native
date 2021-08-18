package ingameIME.context.composition

import ingameIME.utils.ListenableHolder

typealias Commit = String

/**
 * Composition - Manage state of [PreEdit] & [CandidateList]
 */
abstract class Composition(
    val preEdit: PreEdit,
    val candidateList: CandidateList,
) {
    /**
     * If user editing a composition
     * when true [PreEdit] and [CandidateList] should be rendered
     */
    val composing: ListenableHolder<Boolean> = ListenableHolder(false)

    /**
     * Commit from input method, conversion result of [PreEdit] / punctuation / other user inputs
     *
     * @Note conversion of punctuation or some other inputs may not trigger [composing] change
     */
    val commit: ListenableHolder<Commit> = ListenableHolder("")

    /**
     * Terminate active composition
     */
    abstract fun terminate()
}
