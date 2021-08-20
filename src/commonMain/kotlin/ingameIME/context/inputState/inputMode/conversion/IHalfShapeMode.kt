package ingameIME.context.inputState.inputMode.conversion

import ingameIME.context.inputState.IInputState

/**
 * If input method in half shape mode
 *
 * Conflict with [IFullShapeMode]
 * Punctuation/Ascii chars conversion mode
 * Normally, punctuation/ascii chars take up one standard char position
 * In full-shape mode, they take up two standard char position
 * In half-shape mode, they take up one position as normal
 */
interface IHalfShapeMode : IConversionMode {
    /**
     * If the state can co-exist with the state in
     */
    override fun accept(state: IInputState): Boolean {
        return when (state) {
            is IFullShapeMode -> false
            else -> true
        }
    }
}