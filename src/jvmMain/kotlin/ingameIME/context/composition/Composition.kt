package ingameIME.context.composition

class Composition(preEdit: PreEdit, candidateList: ACandidateList) : AComposition(preEdit, candidateList) {
    /**
     * Terminate active composition
     */
    external override fun terminate()
}