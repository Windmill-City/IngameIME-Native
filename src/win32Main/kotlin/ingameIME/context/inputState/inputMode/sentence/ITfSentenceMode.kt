package ingameIME.context.inputState.inputMode.sentence

import platform.win32.libtf.libtf_SentenceMode

fun interface ITfSentenceMode {
    /**
     * Check whether the current mode contains itself
     */
    fun check(mode: libtf_SentenceMode): Boolean
}