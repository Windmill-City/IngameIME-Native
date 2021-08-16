package ingameIME

import ingameIME.context.IInputContext
import ingameIME.context.inputState.IInputState
import ingameIME.context.inputState.imState.IAllowIM
import ingameIME.context.inputState.imState.IForbidIM
import ingameIME.context.inputState.inputMode.conversion.IAlphaNumericMode
import ingameIME.context.inputState.inputMode.conversion.INativeMode
import ingameIME.profile.IInputProcessorProfile

/**
 * IngameIME - Change the state of [IInputContext]
 *
 * @Note Global Object share between threads
 */
interface IIngameIME {
    /**
     * System available input processors
     */
    val availableInputProcessors: List<IInputProcessorProfile>

    /**
     * System available input states
     *
     * @see ingameIME.context.inputState
     */
    val availableInputStates: List<IInputState>

    /**
     * Default IM state that can enable input method
     */
    val defaultAllowIM: IAllowIM

    /**
     * Default IM state that can disable input method
     */
    val defaultForbidIM: IForbidIM

    /**
     * Default Conversion Mode that sets [IAlphaNumericMode]
     */
    val defaultAlphaNumericMode: IAlphaNumericMode

    /**
     * Default Conversion Mode that sets [INativeMode]
     */
    val defaultNativeMode: INativeMode
}