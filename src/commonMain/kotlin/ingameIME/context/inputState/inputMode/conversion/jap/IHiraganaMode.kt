package ingameIME.context.inputState.inputMode.conversion.jap

import ingameIME.context.inputState.IInputState
import ingameIME.context.inputState.inputMode.conversion.IConversionMode

/**
 * If input method in Hiragana mode
 *
 * Conflict with [IRomanMode] and [IKatakanaMode]
 * Japanese pre edit conversion mode
 * Kana - phonograms of Japanese words, used to form Candidates
 * @see ingameIME.context.composition.PreEdit
 * @see ingameIME.context.composition.Candidate
 */
interface IHiraganaMode : IConversionMode {
    /**
     * If the state can co-exist with the state in
     */
    override fun accept(state: IInputState): Boolean {
        return when (state) {
            is IKatakanaMode, is IRomanMode -> false
            else -> true
        }
    }
}
