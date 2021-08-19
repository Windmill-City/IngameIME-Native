package ingameIME.context.inputState.inputMode.conversion

import platform.win32.libtf.libtf_ConversionMode

fun interface ITfConversionMode {
    /**
     * Check whether the current mode contains itself
     */
    fun check(mode: libtf_ConversionMode): Boolean
}