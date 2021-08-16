package ingameIME.context.inputState.imState

import ingameIME.context.inputState.IInputState

/**
 * Common interface for managing input method state
 */
interface IIMState : IInputState {
    /**
     * IIMState always conflicts with each other
     */
    override fun accept(state: IInputState): Boolean {
        return false
    }
}