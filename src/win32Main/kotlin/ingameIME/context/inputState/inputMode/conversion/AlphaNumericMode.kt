package ingameIME.context.inputState.inputMode.conversion

import ingameIME.context.IInputContext
import ingameIME.context.InputContext
import ingameIME.win32.succeedOrThr
import kotlinx.cinterop.memScoped
import platform.win32.libtf.TF_CONVERSIONMODE_ALPHANUMERIC
import platform.win32.libtf.libtf_ConversionMode
import platform.win32.libtf.libtf_set_conversion_mode

object AlphaNumericMode : IAlphaNumericMode, ITfConversionMode {
    /**
     * Check whether the current mode contains itself
     */
    override fun check(mode: libtf_ConversionMode): Boolean {
        return mode == TF_CONVERSIONMODE_ALPHANUMERIC
    }

    override fun onApplyState(context: IInputContext) {
        (context as InputContext).also {
            memScoped {
                libtf_set_conversion_mode(
                    it.nativeContext,
                    TF_CONVERSIONMODE_ALPHANUMERIC
                ).succeedOrThr("libtf_set_conversion_mode")
            }
        }
    }
}