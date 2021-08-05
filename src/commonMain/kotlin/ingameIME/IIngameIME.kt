package ingameIME

import ingameIME.context.IInputContext
import ingameIME.context.inputState.IAllowIM
import ingameIME.context.inputState.IForbidIM
import ingameIME.context.inputState.IInputState
import ingameIME.context.inputState.inputMode.conversion.IAlphaNumericMode
import ingameIME.context.inputState.inputMode.conversion.INativeMode
import ingameIME.profile.ILanguageProfile

/**
 * IngameIME - Change the state of [IInputContext]
 *
 * @Note Global Object share between threads
 */
interface IIngameIME {
    /**
     * System default language profile
     */
    val defaultLanguageProfile: ILanguageProfile

    /**
     * Stores all available input state actions
     *
     * @see ingameIME.context.inputState
     */
    val defaultInputStates: List<IInputState>

    /**
     * Default IM state that allows input method
     */
    val defaultAllowIM: IAllowIM

    /**
     * Default IM state that dis-allow input method
     */
    val defaultForbidIM: IForbidIM

    /**
     * Default Conversion that sets [IAlphaNumericMode]
     */
    val defaultAlphaNumericMode: IAlphaNumericMode

    /**
     * Default Conversion that sets [INativeMode]
     */
    val defaultNativeMode: INativeMode
}