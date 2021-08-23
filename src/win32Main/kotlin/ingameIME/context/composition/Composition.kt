package ingameIME.context.composition

import ingameIME.context.InputContext
import ingameIME.win32.succeedOrThr
import platform.win32.libtf.libtf_terminate_composition

class Composition(val inputContext: InputContext, preEdit: PreEdit, candidateList: ACandidateList) :
    AComposition(preEdit, candidateList) {
    /**
     * Terminate active composition
     */
    override fun terminate() {
        libtf_terminate_composition(inputContext.nativeContext).succeedOrThr()
    }
}