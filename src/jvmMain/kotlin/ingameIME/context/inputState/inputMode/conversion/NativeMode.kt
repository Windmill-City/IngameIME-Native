package ingameIME.context.inputState.inputMode.conversion

import ingameIME.context.IInputContext

object NativeMode : INativeMode {
    external override fun onApplyState(context: IInputContext)
}