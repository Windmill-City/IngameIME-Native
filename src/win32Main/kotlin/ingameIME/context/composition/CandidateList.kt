package ingameIME.context.composition

import ingameIME.context.InputContext
import ingameIME.win32.succeedOrThr
import platform.win32.libtf.libtf_final_candidate_list_sel
import platform.win32.libtf.libtf_set_candidate_list_sel

class CandidateList(val inputContext: InputContext) : ACandidateList() {
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
    override fun setSelection(index: Int) {
        libtf_set_candidate_list_sel(inputContext.nativeContext, index.toUInt()).succeedOrThr()
    }

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
    override fun setFinalize(index: Int) {
        libtf_final_candidate_list_sel(inputContext.nativeContext, index.toUInt()).succeedOrThr()
    }
}