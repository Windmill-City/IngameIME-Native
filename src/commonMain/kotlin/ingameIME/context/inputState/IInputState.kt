package ingameIME.context.inputState

import ingameIME.context.IInputContext
import ingameIME.utils.Observable

/**
 * Input State - Action on [IInputContext] when state change
 *
 * @Note the action here should be stateless
 */
interface IInputState {
    fun onApplyState(context: IInputContext) {}
    fun onLeaveState(context: IInputContext) {}
}

/**
 * Observable [IInputState] - Create property in [IInputContext]
 */
fun <T : IInputState> IInputContext.stateOf(initialValue: T) =
    Observable(initialValue) { _, oldValue, newValue ->
        oldValue.onLeaveState(this)
        newValue.onApplyState(this)
    }


