package ingameIME.context.inputState.inputMode.conversion.jap

import ingameIME.context.inputState.IInputState
import ingameIME.context.inputState.inputMode.conversion.IConversionMode

/**
 * If input method in Roman mode
 *
 * Conflict with [IKatakanaMode] and [IHiraganaMode]
 * Japanese pre edit conversion mode
 * Roman - phonograms of Japanese words, used to form Candidates
 * @see ingameIME.context.composition.PreEdit
 * @see ingameIME.context.composition.Candidate
 */
interface IRomanMode : IConversionMode {
    /**
     * If the state can co-exist with the state in
     */
    override fun accept(state: IInputState): Boolean {
        return when (state) {
            is IHiraganaMode, is IKatakanaMode -> false
            else -> true
        }
    }
}