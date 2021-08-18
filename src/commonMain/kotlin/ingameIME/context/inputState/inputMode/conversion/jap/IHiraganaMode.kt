package ingameIME.context.inputState.inputMode.conversion.jap

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
interface IHiraganaMode : IConversionMode