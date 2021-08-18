package ingameIME.context.inputState

import ingameIME.context.IInputContext

/**
 * Input State - Action on [IInputContext] when state change
 *
 * @Note the action here should be stateless
 */
interface IInputState {
    fun onApplyState(context: IInputContext) {}
    fun onLeaveState(context: IInputContext) {}

    /**
     * If the state can co-exist with the state in
     */
    fun accept(state: IInputState): Boolean
}
