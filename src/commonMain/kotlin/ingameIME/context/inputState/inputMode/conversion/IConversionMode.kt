package ingameIME.context.inputState.inputMode.conversion

import ingameIME.context.inputState.IInputState

/**
 * Conversion mode of the context
 */
interface IConversionMode : IInputState

/**
 * Fallback value of [IConversionMode]
 */
object UnknownConversionMode : IConversionMode {
    /**
     * Always conflicts with others, so it can be removed when apply new states
     */
    override fun accept(state: IInputState): Boolean {
        return false
    }
}
