package ingameIME.context.inputState.inputMode.conversion

import ingameIME.context.IInputContext
import ingameIME.context.inputState.inputMode.AMultiStateHolder
import ingameIME.context.inputState.inputMode.conversion.jap.HiraganaMode
import ingameIME.context.inputState.inputMode.conversion.jap.KatakanaMode
import ingameIME.context.inputState.inputMode.conversion.jap.RomanMode
import platform.win32.libtf.libtf_ConversionMode

class ConversionStateHolder(
    inputContext: IInputContext,
    initialValue: List<IConversionMode>
) : AMultiStateHolder<IConversionMode>(inputContext, initialValue)

/**
 * Convert the native mode to wrapped modes
 */
fun libtf_ConversionMode.toWrappedConversionMode(): List<IConversionMode> {
    return listOf(
        AlphaNumericMode,
        NativeMode,
        FullShapeMode,
        HalfShapeMode,
        HiraganaMode,
        KatakanaMode,
        RomanMode
    ).filter { it.check(this) }
}