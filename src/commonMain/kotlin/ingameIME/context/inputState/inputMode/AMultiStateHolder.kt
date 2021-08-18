package ingameIME.context.inputState.inputMode

import ingameIME.context.IInputContext
import ingameIME.context.inputState.IInputState
import ingameIME.utils.ListenableHolder

/**
 * Holder of input context current states
 */
abstract class AMultiStateHolder<T : IInputState>(private val inputContext: IInputContext, initialValue: List<T>) :
    ListenableHolder<List<T>>(initialValue) {
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