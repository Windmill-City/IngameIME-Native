package ingameIME.context.inputState.inputMode.sentence

import ingameIME.context.IInputContext
import ingameIME.context.inputState.inputMode.AMultiStateHolder
import platform.win32.libtf.libtf_SentenceMode

class SentenceStateHolder(
    inputContext: IInputContext,
    initialValue: List<ISentenceMode>
) : AMultiStateHolder<ISentenceMode>(inputContext, initialValue)

/**
 * Convert the native mode to wrapped modes
 */
@Suppress("unused")
fun libtf_SentenceMode.toWrappedSentenceMode(): List<ISentenceMode> {
    return listOf()
}
