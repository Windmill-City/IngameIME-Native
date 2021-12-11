package ingameIME.context.inputState.inputMode.conversion

import ingameIME.context.IInputContext
import ingameIME.context.InputContext
import ingameIME.win32.succeedOrThr
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import platform.win32.libtf.*

object HalfShapeMode : IHalfShapeMode, ITfConversionMode {
    /**
     * Check whether the current mode contains itself
     */
    override fun check(mode: libtf_ConversionMode): Boolean {
        return mode and TF_CONVERSIONMODE_FULLSHAPE == 0
    }

    override fun onApplyState(context: IInputContext) {
        (context as InputContext).also {
            memScoped {
                val mode: libtf_ConversionModeVar = this.alloc()
                libtf_get_conversion_mode(it.nativeContext, mode.ptr).succeedOrThr("libtf_get_conversion_mode")
                mode.value = mode.value and TF_CONVERSIONMODE_FULLSHAPE.inv()
                libtf_set_conversion_mode(it.nativeContext, mode.value).succeedOrThr("libtf_set_conversion_mode")
            }
        }
    }
}