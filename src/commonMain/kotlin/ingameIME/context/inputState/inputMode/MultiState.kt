package ingameIME.context.inputState.inputMode

import ingameIME.context.inputState.IInputState

/**
 * Holds input method current states
 */
abstract class MultiState<T : IInputState> {
    /**
     * Apply new state to the input method
     * The states who conflict with the new one will be removed
     */
    abstract fun apply(state: T)

    /**
     * Check if any existing state conflicts with it
     */
    abstract fun canApply(state: T): Boolean

    /**
     * Check if specific state exists
     */
    abstract fun has(state: T): Boolean
}