package ingameIME.context.inputState.inputMode.conversion

import ingameIME.context.inputState.IInputState

/**
 * Conversion mode of the context
 */
interface IConversionMode : IInputState

/**
 * Fallback value of [IConversionMode]
 */
object UnknownConversionMode : IConversionMode
