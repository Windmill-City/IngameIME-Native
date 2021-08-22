package ingameIME.context

import ingameIME.IngameIME
import ingameIME.context.composition.*
import ingameIME.context.inputState.MultiStateHolder
import ingameIME.context.inputState.StateHolder
import ingameIME.context.inputState.imState.IIMState
import ingameIME.context.inputState.inputMode.conversion.IConversionMode
import ingameIME.context.inputState.inputMode.conversion.jap.HiraganaMode
import ingameIME.context.inputState.inputMode.sentence.ISentenceMode
import ingameIME.context.inputState.inputMode.toWrappedConversionMode
import ingameIME.context.inputState.inputMode.toWrappedSentenceMode
import ingameIME.profile.IInputProcessorProfile
import ingameIME.profile.InputProcessorProfile
import ingameIME.profile.toWrappedProfile
import ingameIME.utils.ListenableHolder
import ingameIME.win32.succeedOrThr
import ingameIME.win32.toKString
import kotlinx.cinterop.*
import platform.win32.libtf.*
import platform.windows.HWND
import platform.zlib.voidpVar
import kotlin.native.concurrent.ThreadLocal

/**
 * For getting instance from callback
 */
@ThreadLocal
lateinit var instanceOfInputContext: InputContext

/**
 * Create Input Context for calling thread
 *
 * @param defaultFontHeight [ingameIME.context.composition.PreEdit.defaultFontHeight]
 * @see IInputContext
 */
class InputContext(defaultFontHeight: Int) : IInputContext {
    val nativeContext: libtf_pInputContextVar = nativeHeap.alloc()

    /**
     * [AComposition] of the context
     */
    override val composition: AComposition =
        Composition(this, PreEdit().apply { this.defaultFontHeight = defaultFontHeight }, CandidateList(this))

    /**
     * Current active input processor of the context
     *
     * @usage Assign a new value to change the active one
     * Normally, we don't need to change it, but just display which it is on the screen
     * User can change this by system specified hotkey
     */
    override val inputProcessor: ListenableHolder<IInputProcessorProfile>

    /**
     * Current [IIMState] of the context, set if input method is enabled
     *
     * @usage Assign a new value to change the active one
     * @see [ingameIME.context.inputState.imState.IAllowIM]
     * @see [ingameIME.context.inputState.imState.IForbidIM]
     */
    override val imState: StateHolder<IIMState>

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
    override val conversionMode: MultiStateHolder<IConversionMode>

    /**
     * Current [ISentenceMode] of the context
     *
     * @usage Assign a new value to change the active one
     * Normally, we don't need to change it, but just display which it is on the screen
     * User can change this by system specified hotkey
     */
    override val sentenceMode: MultiStateHolder<ISentenceMode>

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
        get() {
            return memScoped {
                val curState: BooleanVar = this.alloc()
                libtf_get_full_screen(nativeContext.value, curState.ptr).succeedOrThr()
                curState.value
            }
        }
        set(value) {
            libtf_set_full_screen(nativeContext.value, value).succeedOrThr()
        }

    /**
     * Set to true to dispose the native handle
     */
    override var disposed: Boolean = false
        set(value) {
            if (!value) throw Error("Set false to dispose")

            //Dispose native context
            libtf_dispose_ctx(nativeContext.value).succeedOrThr()

            field = value
        }

    init {
        //Create native context
        libtf_create_ctx(nativeContext.ptr).succeedOrThr()

        //Initial active inputProcessor
        inputProcessor = memScoped {
            val profile: libtf_InputProcessorProfile_t = this.alloc()
            libtf_get_active_input_processor(profile.ptr).succeedOrThr()
            object : ListenableHolder<IInputProcessorProfile>(profile.toWrappedProfile()) {
                override fun setProperty(newValue: IInputProcessorProfile) {
                    libtf_set_active_input_processor((newValue as InputProcessorProfile).nativeProfile).succeedOrThr()
                    super.setProperty(newValue)
                }
            }
        }

        //Initial imState
        imState = memScoped {
            val curState: BooleanVar = this.alloc()
            libtf_get_im_state(nativeContext.value, curState.ptr).succeedOrThr()
            StateHolder(this@InputContext, if (curState.value) IngameIME.defaultAllowIM else IngameIME.defaultForbidIM)
        }

        //Initial Conversion State
        conversionMode = memScoped {
            val curMode: libtf_ConversionModeVar = this.alloc()
            libtf_get_conversion_mode(nativeContext.value, curMode.ptr).succeedOrThr()
            MultiStateHolder(this@InputContext, curMode.value.toWrappedConversionMode())
        }

        //Initial Sentence State
        sentenceMode = memScoped {
            val curMode: libtf_SentenceModeVar = this.alloc()
            libtf_get_sentence_mode(nativeContext.value, curMode.ptr).succeedOrThr()
            MultiStateHolder(this@InputContext, curMode.value.toWrappedSentenceMode())
        }

        //Register callbacks
        instanceOfInputContext = this
        //Composition - PreEdit
        libtf_set_composition_callback(
            nativeContext.value,
            staticCFunction { composition: libtf_Composition_t, _: voidpVar ->
                with(instanceOfInputContext) {
                    when (composition.state) {
                        libtf_CompositionState.libtf_CompositionBegin -> this.composition.composing.setProperty(true)
                        libtf_CompositionState.libtf_CompositionUpdate -> {
                            //Construct PreEdit Context obj
                            this.composition.preEdit.context.setProperty(
                                PreEdit.Context(
                                    composition.preEdit!!.toKString(false),
                                    IntRange(composition.selection[0], composition.selection[1])
                                )
                            )
                        }
                        libtf_CompositionState.libtf_CompositionEnd -> {
                            this.composition.preEdit.context.setProperty(PreEdit.Context("", IntRange.EMPTY))
                            this.composition.composing.setProperty(false)
                        }
                    }
                }
            }.reinterpret(), null
        )

        //Composition - Commit
        libtf_set_commit_callback(
            nativeContext.value, staticCFunction { commit: libtf_Commit, _: voidpVar ->
                with(instanceOfInputContext) {
                    this.composition.commit.setProperty(commit.toKString(false))
                }
            }.reinterpret(), null
        )

        //Composition - Bounding Box
        libtf_set_bounding_box_callback(
            nativeContext.value, staticCFunction { rect: libtf_BoundingBox_t, _: voidpVar ->
                with(instanceOfInputContext) {
                    with(this.composition.preEdit.boundingBox) {
                        rect.left = this.x
                        rect.top = this.y
                        rect.right = this.x + this.width
                        rect.bottom = this.y + this.height
                    }
                }
            }.reinterpret(), null
        )

        //Composition - CandidateList
        libtf_set_candidate_list_callback(
            nativeContext.value, staticCFunction { candidateList: libtf_CandidateList_t, _: voidpVar ->
                with(instanceOfInputContext) {
                    when (candidateList.state) {
                        libtf_CandidateListState.libtf_CandidateListBegin, libtf_CandidateListState.libtf_CandidateListEnd ->
                            //Clear the context
                            this.composition.candidateList.context.setProperty(
                                ACandidateList.Context(
                                    listOf(),
                                    IntRange.EMPTY,
                                    -1
                                )
                            )
                        libtf_CandidateListState.libtf_CandidateListUpdate -> {
                            val candidates = mutableListOf<Candidate>()
                            for (i in 0 until candidateList.totalCount.toInt())
                                candidates.add(candidateList.candidates!![i]!!.toKString(false))
                            //Construct data context
                            this.composition.candidateList.context.setProperty(
                                ACandidateList.Context(
                                    candidates,
                                    IntRange(candidateList.pageStart.toInt(), candidateList.pageEnd.toInt()),
                                    candidateList.curSelection.toInt()
                                )
                            )
                        }
                    }
                }
            }.reinterpret(), null
        )

        //Conversion Mode
        libtf_set_conversion_mode_callback(
            nativeContext.value, staticCFunction { mode: libtf_ConversionMode, _: voidpVar ->
                with(instanceOfInputContext) {
                    val wrappedMode = mode.toWrappedConversionMode()
                        .filterNot {
                            //Only Japanese input method has HiraganaMode
                            it is HiraganaMode
                                    && !this.inputProcessor.getProperty().locale.startsWith("ja")
                        }
                    with(wrappedMode) {
                        conversionMode.onChange(conversionMode.getProperty(), this)
                        conversionMode.setPropertyInternal(this)
                    }
                }
            }.reinterpret(), null
        )

        //Sentence Mode
        libtf_set_sentence_mode_callback(
            nativeContext.value, staticCFunction { mode: libtf_SentenceMode, _: voidpVar ->
                with(instanceOfInputContext) {
                    with(mode.toWrappedSentenceMode()) {
                        sentenceMode.onChange(sentenceMode.getProperty(), this)
                        sentenceMode.setPropertyInternal(this)
                    }
                }
            }.reinterpret(), null
        )

        //InputProcessor
        libtf_set_input_processor_callback(
            nativeContext.value,
            staticCFunction { profile: libtf_InputProcessorProfile_t, _: voidpVar ->
                if (profile.activated)
                    with(instanceOfInputContext) {
                        with(profile.toWrappedProfile()) {
                            inputProcessor.onChange(inputProcessor.getProperty(), this)
                            inputProcessor.setPropertyInternal(this)
                        }
                    }
            }.reinterpret(), null
        )
    }

    /**
     * It's required to call this method to set the correct input method state,
     * when the window received WM_SETFOCUS or WM_KILLFOCUS
     *
     * @param hWnd Win32 handle of the window
     * @param msg can be WM_SETFOCUS or WM_KILLFOCUS
     */
    fun onWindowFocusMsg(hWnd: HWND, msg: UInt) {
        libtf_on_focus_msg(nativeContext.value, hWnd, msg).succeedOrThr()
    }
}