package ingameIME.context.composition

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
    var composing: Boolean = false
        protected set

    /**
     * Commit from input method, conversion result of [PreEdit] / punctuation / other user inputs
     *
     * @Note conversion of punctuation or some other inputs may not trigger [composing] change
     */
    var commit: Commit = ""
        protected set

    /**
     * Terminate active composition
     */
    abstract fun terminate()
}
