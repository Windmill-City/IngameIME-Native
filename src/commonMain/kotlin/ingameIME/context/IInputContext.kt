package ingameIME.context

import ingameIME.IIngameIME
import ingameIME.context.composition.Composition
import ingameIME.context.inputState.IIMState
import ingameIME.context.inputState.IInputState
import ingameIME.context.inputState.inputMode.conversion.IConversionMode
import ingameIME.context.inputState.inputMode.sentence.ISentenceMode
import ingameIME.profile.IInputMethodProfile
import ingameIME.profile.ILanguageProfile

/**
 * Input Context - Manage input method state
 *
 * @Note Input Context should be thread local
 */
interface IInputContext {
    /**
     * The creator of the context
     */
    val ingameIME: IIngameIME

    /**
     * Composition of the context
     *
     * Observable value
     * @see ingameIME.utils.setCallback
     */
    val composition: Composition

    /**
     * Current active language profile of the context
     *
     * Normally, we don't need to change active language, but display it on screen
     * User can use hotkey to change this themselves
     *
     * As the input method(IM) always associate with the language, we just need to display language when no active IM
     *
     * Observable value
     * @see ingameIME.utils.setCallback
     */
    var langProfile: ILanguageProfile

    /**
     * Current active input method of the context
     *
     * Normally, we don't need to change active input method(IM), but display what IM is on screen,
     * like the conversion mode popup
     *
     * User can use hotkey to change this themselves
     *
     * Observable value
     * @see ingameIME.utils.setCallback
     */
    var inputMethod: IInputMethodProfile

    /**
     * Current [IInputState] of the context, set if input method is enabled
     *
     * @see [ingameIME.context.inputState.IAllowIM]
     * @see [ingameIME.context.inputState.IForbidIM]
     *
     * Should call [IInputState.onApplyState] or [IInputState.onLeaveState] when changing
     * @see [ingameIME.context.inputState.stateOf]
     *
     * Observable value
     * @see ingameIME.utils.setCallback
     */
    var imState: IIMState

    /**
     * Current [IConversionMode] of the context
     *
     * Normally, we don't need to change conversion mode, but just render this state on screen
     * User can use input method(IM)'s hotkey to change this mode themselves
     *
     * When IM is enabled, we need to show a popup(last for 3-5 seconds) to tell user what conversion mode is
     *
     * @see [ingameIME.context.inputState.inputMode.conversion.IAlphaNumericMode]
     * @see [ingameIME.context.inputState.inputMode.conversion.INativeMode]
     *
     * Should call [IInputState.onApplyState] or [IInputState.onLeaveState] when changing
     * @see [ingameIME.context.inputState.stateOf]
     *
     * Observable value
     * @see ingameIME.utils.setCallback
     */
    var conversionMode: IConversionMode

    /**
     * Current [ISentenceMode] of the context
     *
     * Should call [IInputState.onApplyState] or [IInputState.onLeaveState] when changing
     * @see [ingameIME.context.inputState.stateOf]
     *
     * Observable value
     * @see ingameIME.utils.setCallback
     */
    var sentenceMode: ISentenceMode

    /**
     * If in UI-Less Mode
     *
     * When set to true, input method(IM)'s
     * [Composition] Window & [ingameIME.context.composition.CandidateList] Window will not draw,
     * so we need to draw it ourselves
     *
     * @Note this should be true when the game is in full screen mode, as we directly write to the frame buffer,
     * it is impossible to show another window on the screen at the same time
     */
    var uiLess: Boolean

    /**
     * Listen for this to receive the conversion result of [Composition.preEdit]
     */
    var commitListener: ICommitListener
}