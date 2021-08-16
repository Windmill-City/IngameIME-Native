package ingameIME.context.inputState.inputMode.conversion

/**
 * If input method in half shape mode
 *
 * Conflict with [IHalfShapeMode]
 * Punctuation/Ascii chars conversion mode
 * Normally, punctuation/ascii chars take up one standard char position
 * In full-shape mode, they take up two standard char position
 * In half-shape mode, they take up one position as normal
 */
interface IFullShapeMode