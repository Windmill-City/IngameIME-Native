package ingameIME.context.inputState.inputMode

import ingameIME.context.IInputContext
import ingameIME.context.inputState.IInputState
import ingameIME.utils.ListenableHolder

/**
 * Holder of input context current states
 */
@Suppress("MemberVisibilityCanBePrivate")
abstract class AMultiStateHolder<T : IInputState>(protected val inputContext: IInputContext, initialValue: List<T>) :
    ListenableHolder<List<T>>(initialValue) {
    /**
     * Apply new state to the input method
     * The states who conflict with the new one will be removed
     */
    open fun apply(state: T) {
        //Filter out conflict states
        val new = mutableListOf<T>()
        getProperty().filterTo(new) { it.accept(state).apply { if (!this) it.onLeaveState(inputContext) } }

        new.add(state.apply { onApplyState(inputContext) })

        setProperty(new)
    }

    /**
     * Check if any existing state conflicts with it
     */
    open fun canApply(state: T): Boolean {
        return getProperty().any { !it.accept(state) }
    }

    /**
     * Check if specific state exists
     */
    open fun has(state: T): Boolean {
        return getProperty().contains(state)
    }
}