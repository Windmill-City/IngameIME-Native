package ingameIME.profile

typealias LangCode = String

/**
 * Profile of a language
 *
 * A language profile contains multiply [IInputMethodProfile]
 *
 * @see IInputMethodProfile
 */
interface ILanguageProfile {
    /**
     * Language code of the language profile
     */
    val langCode: LangCode

    /**
     * System default input method of the language profile
     */
    val defaultInputMethod: IInputMethodProfile

    /**
     * Input methods this language profile contains
     */
    val inputMethods: List<IInputMethodProfile>
}

/**
 * Fallback value of [ILanguageProfile]
 */
object UnknownLanguageProfile : ILanguageProfile {
    override val langCode: LangCode get() = "en-US"
    override val defaultInputMethod: IInputMethodProfile get() = UnknownInputMethodProfile
    override val inputMethods: List<IInputMethodProfile> get() = emptyList()
}