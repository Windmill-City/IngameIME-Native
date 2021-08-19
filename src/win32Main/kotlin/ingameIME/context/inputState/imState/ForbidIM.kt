package ingameIME.context.inputState.imState

import ingameIME.context.IInputContext
import ingameIME.context.InputContext
import ingameIME.win32.succeedOrThr
import kotlinx.cinterop.value
import platform.win32.libtf.libtf_set_im_state

/**
 * Disable input method for Win32 Context only
 */
object ForbidIM : IForbidIM {
    override fun onApplyState(context: IInputContext) {
        libtf_set_im_state((context as InputContext).nativeContext.value, false).succeedOrThr()
    }
}