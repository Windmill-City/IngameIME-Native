package ingameIME.context.inputState.imState

import ingameIME.context.IInputContext

object AllowIM : IAllowIM {
    external override fun onApplyState(context: IInputContext)
}