package ingameIME.context.inputState.inputMode.conversion

import ingameIME.context.inputState.IInputState

/**
 * Native Mode - Input method will start composition when user typed in words that can form preEdit
 *
 * Conflict with [IAlphaNumericMode]
 * @see ingameIME.context.composition.AComposition
 */
interface INativeMode : IConversionMode {
    /**
     * If the state can co-exist with the state in
     */
    override fun accept(state: IInputState): Boolean {
        return when (state) {
            is IAlphaNumericMode -> false
            else -> true
        }
    }
}