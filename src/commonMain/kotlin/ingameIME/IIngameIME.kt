package ingameIME

import ingameIME.context.IInputContext
import ingameIME.context.inputState.IInputState
import ingameIME.context.inputState.imState.IAllowIM
import ingameIME.context.inputState.imState.IForbidIM
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
     * System available language profiles
     */
    val availableLanguageProfiles: List<ILanguageProfile>

    /**
     * System available input states
     *
     * @see ingameIME.context.inputState
     */
    val defaultInputStates: List<IInputState>

    /**
     * Default IM state that can enable input method
     */
    val defaultAllowIM: IAllowIM

    /**
     * Default IM state that can disable input method
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