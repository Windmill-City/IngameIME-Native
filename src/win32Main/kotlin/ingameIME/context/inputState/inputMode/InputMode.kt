package ingameIME.context.inputState.inputMode

import ingameIME.context.inputState.inputMode.conversion.*
import ingameIME.context.inputState.inputMode.conversion.jap.HiraganaMode
import ingameIME.context.inputState.inputMode.conversion.jap.KatakanaMode
import ingameIME.context.inputState.inputMode.conversion.jap.RomanMode
import ingameIME.context.inputState.inputMode.sentence.ISentenceMode
import platform.win32.libtf.libtf_ConversionMode
import platform.win32.libtf.libtf_SentenceMode

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

/**
 * Convert the native mode to wrapped modes
 */
@Suppress("unused")
fun libtf_SentenceMode.toWrappedSentenceMode(): List<ISentenceMode> {
    return listOf()
}
