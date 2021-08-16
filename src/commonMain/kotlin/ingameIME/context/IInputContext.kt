package ingameIME.context

import ingameIME.context.composition.Composition
import ingameIME.context.inputState.IInputState
import ingameIME.context.inputState.imState.IIMState
import ingameIME.context.inputState.inputMode.MultiState
import ingameIME.context.inputState.inputMode.conversion.IConversionMode
import ingameIME.context.inputState.inputMode.sentence.ISentenceMode
import ingameIME.profile.IInputProcessorProfile
import ingameIME.utils.IDispose

/**
 * Input Context - Manage input method state
 *
 * @Note Context should be thread local
 * @Note A thread can create at most one context
 */
interface IInputContext : IDispose {
    /**
     * [Composition] of the context
     */
    val composition: Composition

    /**
     * Current active input processor of the context
     *
     * @usage Assign a new value to change the active one
     * Normally, we don't need to change it, but just display which it is on the screen
     * User can change this by system specified hotkey
     */
    var inputProcessor: IInputProcessorProfile

    /**
     * Current [IIMState] of the context, set if input method is enabled
     *
     * @usage Assign a new value to change the active one
     * @see [ingameIME.context.inputState.imState.IAllowIM]
     * @see [ingameIME.context.inputState.imState.IForbidIM]
     *
     * Should call [IInputState.onApplyState] or [IInputState.onLeaveState] when changing
     * @see [ingameIME.context.inputState.stateOf]
     */
    var imState: IIMState

    /**
     * Current [IConversionMode] of the context
     *
     * @usage Assign a new value to change the active one
     * Normally, we don't need to change it, but just display which it is on the screen
     * User can change this by system specified hotkey
     *
     * When IM is enabled, we need to tell user what current mode is by showing
     * a popup(last for 3-5 seconds) on screen
     */
    val conversionMode: MultiState<IConversionMode>

    /**
     * Current [ISentenceMode] of the context
     *
     * @usage Assign a new value to change the active one
     * Normally, we don't need to change it, but just display which it is on the screen
     * User can change this by system specified hotkey
     */
    val sentenceMode: MultiState<ISentenceMode>

    /**
     * If in UI-Less Mode
     *
     * When set to true, input method(IM)'s
     * [Composition] Window and [ingameIME.context.composition.CandidateList] Window will not draw
     *
     * @Note this should be set to true when the game is in full screen mode,
     * it is impossible to show another window on the screen as we directly write to the frame buffer
     *
     * @Note This value can be changed at any time,
     * but it is suggested to call [Composition.terminate] after this value has changed
     */
    var uiLess: Boolean
}