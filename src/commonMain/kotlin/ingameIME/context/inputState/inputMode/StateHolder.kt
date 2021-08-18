package ingameIME.context.inputState.inputMode

import ingameIME.context.IInputContext
import ingameIME.context.inputState.IInputState
import ingameIME.utils.ListenableHolder

/**
 * Holder of input context current state
 */
@Suppress("MemberVisibilityCanBePrivate")
open class StateHolder<T : IInputState>(protected val inputContext: IInputContext, initialValue: T) :
    ListenableHolder<T>(initialValue) {
    /**
     * Leave the old state and apply the new state when the state changes
     */
    override fun onChange(old: T, new: T): T {
        old.onLeaveState(inputContext)
        new.onApplyState(inputContext)
        return super.onChange(old, new)
    }
}