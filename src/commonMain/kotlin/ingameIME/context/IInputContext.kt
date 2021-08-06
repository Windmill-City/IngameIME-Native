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
     */
    val composition: Composition

    /**
     * Current active language profile of the context
     *
     * @usage Assign a [ILanguageProfile] to change the active one
     * Normally, we don't need to change active language, but display it on screen
     *
     * As the input method(IM) always associate with the language, we just need to display
     * active language when there is no active IM
     *
     * User can use system specified hotkey to change this
     * Observable value
     * @see ingameIME.utils.setCallback
     */
    var langProfile: ILanguageProfile

    /**
     * Current active input method of the context
     *
     * @usage Assign a [IInputMethodProfile] to change the active one
     * Normally, we don't need to change active input method(IM),
     * but display what IM is on screen, like the conversion mode popup
     *
     * User can use system specified hotkey to change this
     * Observable value
     * @see ingameIME.utils.setCallback
     */
    var inputMethod: IInputMethodProfile

    /**
     * Current [IIMState] of the context, set if input method is enabled
     *
     * @usage Assign a [IIMState] to change the active one
     * @see [ingameIME.context.inputState.IAllowIM]
     * @see [ingameIME.context.inputState.IForbidIM]
     *
     * Should call [IInputState.onApplyState] or [IInputState.onLeaveState] when changing
     * @see [ingameIME.context.inputState.stateOf]
     */
    var imState: IIMState

    /**
     * Current [IConversionMode] of the context
     *
     * @usage Assign a [IConversionMode] to change the active one
     * Normally, we don't need to change conversion mode, but just render this state on screen
     * User can use input method(IM)'s hotkey to change this mode themselves
     *
     * When IM is enabled, we need to show a popup(last for 3-5 seconds) to tell user what conversion mode is
     *
     * Should call [IInputState.onApplyState] or [IInputState.onLeaveState] when changing
     * @see [ingameIME.context.inputState.stateOf]
     *
     * May change by input method
     * Observable value
     * @see ingameIME.utils.setCallback
     */
    var conversionMode: IConversionMode

    /**
     * Current [ISentenceMode] of the context
     *
     * @usage Assign a [ISentenceMode] to change the active one
     *
     * Should call [IInputState.onApplyState] or [IInputState.onLeaveState] when changing
     * @see [ingameIME.context.inputState.stateOf]
     *
     * May change by input method
     * Observable value
     * @see ingameIME.utils.setCallback
     */
    var sentenceMode: ISentenceMode

    /**
     * Listen for this to receive the conversion result of [Composition.preEdit]
     */
    var commitListener: ICommitListener

    /**
     * If in UI-Less Mode
     *
     * When set to true, input method(IM)'s
     * [Composition] Window & [ingameIME.context.composition.CandidateList] Window will not draw
     *
     * @Note this should be true when the game is in full screen mode,
     * it is impossible to show another window on the screen as we directly write to the frame buffer
     *
     * @Note this value can be changed on the fly
     */
    var uiLess: Boolean
}