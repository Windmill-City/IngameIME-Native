package ingameIME.context.inputState.inputMode.conversion

import ingameIME.context.IInputContext
import ingameIME.context.InputContext
import ingameIME.context.inputState.IInputState
import ingameIME.win32.succeedOrThr
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import platform.win32.libtf.*

object FullShapeMode : IFullShapeMode, ITfConversionMode {
    /**
     * If the state can co-exist with the state in
     */
    override fun accept(state: IInputState): Boolean {
        return when (state) {
            is IHalfShapeMode -> false
            else -> true
        }
    }

    /**
     * Check whether the current mode contains itself
     */
    override fun check(mode: libtf_ConversionMode): Boolean {
        return mode and TF_CONVERSIONMODE_FULLSHAPE == 1
    }

    override fun onApplyState(context: IInputContext) {
        (context as InputContext).also {
            memScoped {
                val mode: libtf_ConversionModeVar = this.alloc()
                libtf_get_conversion_mode(it.nativeContext.value, mode.ptr).succeedOrThr()
                mode.value = mode.value or TF_CONVERSIONMODE_FULLSHAPE
                libtf_set_conversion_mode(it.nativeContext.value, mode.value).succeedOrThr()
            }
        }
    }
}