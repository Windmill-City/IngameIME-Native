package ingameIME.context.inputState.inputMode.conversion

import ingameIME.context.inputState.IInputState

/**
 * AlphaNumeric Mode - Input method won't start composition
 *
 * Conflict with [INativeMode]
 * @see ingameIME.context.composition.AComposition
 */
interface IAlphaNumericMode : IConversionMode {
    /**
     * If the state can co-exist with the state in
     */
    override fun accept(state: IInputState): Boolean {
        return when (state) {
            is INativeMode -> false
            else -> true
        }
    }
}