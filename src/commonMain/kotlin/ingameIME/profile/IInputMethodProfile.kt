package ingameIME.profile

/**
 * Profile of an input method, associate with the [ILanguageProfile]
 */
interface IInputMethodProfile {
    /**
     * The name of the input method
     */
    val name: String

    /**
     * The language profile the input method associate to
     */
    val languageProfile: ILanguageProfile
}

/**
 * Fallback value of [IInputMethodProfile]
 */
object UnknownInputMethodProfile : IInputMethodProfile {
    override val name: String get() = "[Fallback Value]Unknown Input Method"
    override val languageProfile: ILanguageProfile get() = UnknownLanguageProfile
}
