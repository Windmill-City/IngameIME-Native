package ingameIME.context.composition

import ingameIME.utils.ListenableHolder

typealias Commit = String

/**
 * Composition - Manage state of [APreEdit] & [ACandidateList]
 */
abstract class AComposition(
    val preEdit: APreEdit,
    val candidateList: ACandidateList,
) {
    /**
     * If user editing a composition
     * when true [APreEdit] and [ACandidateList] should be rendered
     */
    val composing: ListenableHolder<Boolean> = ListenableHolder(false)

    /**
     * Commit from input method, conversion result of [APreEdit] / punctuation / other user inputs
     *
     * @Note conversion of punctuation or some other inputs may not trigger [composing] change
     */
    val commit: ListenableHolder<Commit> = ListenableHolder("")

    /**
     * Terminate active composition
     */
    abstract fun terminate()
}
