package ingameIME.context.inputState.imState

import ingameIME.context.IInputContext

object ForbidIM : IForbidIM {
    external override fun onApplyState(context: IInputContext)
}