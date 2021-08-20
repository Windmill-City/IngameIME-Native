package ingameIME.context.composition

class CandidateList : ACandidateList() {
    /**
     * Select another [Candidate] by its index
     *
     * We need to call [setSelection] when user selects [Candidate]s by hovering the mouse
     * if we do the rendering job
     *
     * @param index of [Candidate] to select
     * @see getIndex to get the index
     *
     * @Note should in the range of [ACandidateList.Context.displayRange]
     */
    external override fun setSelection(index: Int)

    /**
     * [Commit] specific [Candidate] by its index
     *
     * We need to call [setFinalize] when user selects [Candidate]s by clicking the mouse
     * if we do the rendering job
     *
     * @param index of [Candidate] to [Commit]
     * @see getIndex to get the index
     *
     * @Note should in the range of [ACandidateList.Context.displayRange]
     */
    external override fun setFinalize(index: Int)
}