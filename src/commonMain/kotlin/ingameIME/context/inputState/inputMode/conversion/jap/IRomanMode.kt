package ingameIME.context.inputState.inputMode.conversion.jap

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
interface IRomanMode : IConversionMode