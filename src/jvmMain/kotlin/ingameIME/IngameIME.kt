package ingameIME

import ingameIME.context.inputState.IInputState
import ingameIME.context.inputState.imState.AllowIM
import ingameIME.context.inputState.imState.ForbidIM
import ingameIME.context.inputState.imState.IAllowIM
import ingameIME.context.inputState.imState.IForbidIM
import ingameIME.context.inputState.inputMode.conversion.*
import ingameIME.context.inputState.inputMode.conversion.jap.HiraganaMode
import ingameIME.context.inputState.inputMode.conversion.jap.KatakanaMode
import ingameIME.context.inputState.inputMode.conversion.jap.RomanMode
import ingameIME.profile.IInputProcessorProfile

object IngameIME : IIngameIME {
    /**
     * System available input processors
     */
    override val availableInputProcessors: List<IInputProcessorProfile>
        external get

    /**
     * System available input states
     *
     * @see ingameIME.context.inputState
     */
    override val availableInputStates: List<IInputState>
        get() = listOf(
            AllowIM,
            ForbidIM,
            AlphaNumericMode,
            NativeMode,
            HalfShapeMode,
            FullShapeMode,
            RomanMode,
            KatakanaMode,
            HiraganaMode
        )

    /**
     * Default IM state that can enable input method
     */
    override val defaultAllowIM: IAllowIM
        get() = AllowIM

    /**
     * Default IM state that can disable input method
     */
    override val defaultForbidIM: IForbidIM
        get() = ForbidIM

    /**
     * Default Conversion Mode that sets [IAlphaNumericMode]
     */
    override val defaultAlphaNumericMode: IAlphaNumericMode
        get() = AlphaNumericMode

    /**
     * Default Conversion Mode that sets [INativeMode]
     */
    override val defaultNativeMode: INativeMode
        get() = NativeMode
}