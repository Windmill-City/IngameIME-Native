package ingameIME.context

import ingameIME.context.composition.AComposition
import ingameIME.context.composition.CandidateList
import ingameIME.context.composition.Composition
import ingameIME.context.composition.PreEdit
import ingameIME.context.inputState.MultiStateHolder
import ingameIME.context.inputState.StateHolder
import ingameIME.context.inputState.imState.IIMState
import ingameIME.context.inputState.inputMode.conversion.IConversionMode
import ingameIME.context.inputState.inputMode.sentence.ISentenceMode
import ingameIME.profile.IInputProcessorProfile
import ingameIME.profile.InputProcessorProfile
import ingameIME.utils.ListenableHolder

/**
 * Create InputContext for the calling thread
 *
 * @param defaultFontHeight [ingameIME.context.composition.PreEdit.defaultFontHeight]
 * @see IInputContext
 */
@Suppress("LeakingThis", "MemberVisibilityCanBePrivate")
abstract class InputContext(defaultFontHeight: Int) : IInputContext {
    init {
        initialize()
    }

    /**
     * Initialize Context
     */
    private external fun initialize()

    /**
     * [AComposition] of the context
     */
    override val composition: AComposition =
        Composition(PreEdit().apply { this.defaultFontHeight = defaultFontHeight }, CandidateList())

    /**
     * Current active input processor of the context
     *
     * @usage Assign a new value to change the active one
     * Normally, we don't need to change it, but just display which it is on the screen
     * User can change this by system specified hotkey
     */
    override val inputProcessor: ListenableHolder<IInputProcessorProfile> =
        object : ListenableHolder<IInputProcessorProfile>(getActiveInputProcessor()) {
            override fun setProperty(newValue: IInputProcessorProfile) {
                setActiveInputProcessor(newValue as InputProcessorProfile)
            }
        }

    /**
     * Current [IIMState] of the context, set if input method is enabled
     *
     * @usage Assign a new value to change the active one
     * @see [ingameIME.context.inputState.imState.IAllowIM]
     * @see [ingameIME.context.inputState.imState.IForbidIM]
     */
    override val imState: StateHolder<IIMState> = StateHolder(this, getIMState())

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
    override val conversionMode: MultiStateHolder<IConversionMode> = MultiStateHolder(this, getConversionMode())

    /**
     * Current [ISentenceMode] of the context
     *
     * @usage Assign a new value to change the active one
     * Normally, we don't need to change it, but just display which it is on the screen
     * User can change this by system specified hotkey
     */
    override val sentenceMode: MultiStateHolder<ISentenceMode> = MultiStateHolder(this, getSentenceMode())

    /**
     * If in UI-Less Mode
     *
     * When set to true, input method(IM)'s
     * [AComposition] Window and [ingameIME.context.composition.ACandidateList] Window will not draw
     *
     * @Note this should be set to true when the game is in full screen mode,
     * it is impossible to show another window on the screen as we directly write to the frame buffer
     *
     * @Note This value can be changed at any time,
     * but it is suggested to call [AComposition.terminate] after this value has changed
     */
    override var uiLess: Boolean
        external get
        external set

    /**
     * Set to true to dispose the native handle
     */
    override var disposed: Boolean
        external get
        external set

    //InputProcessor setter
    protected external fun setActiveInputProcessor(profile: InputProcessorProfile)

    //Initial getters
    protected external fun getActiveInputProcessor(): InputProcessorProfile
    protected external fun getIMState(): IIMState
    protected external fun getConversionMode(): List<IConversionMode>
    protected external fun getSentenceMode(): List<ISentenceMode>
}