package ingameIME.profile

typealias Locale = String

/**
 * Return the localized name of the locale
 */
expect fun Locale.getName(): String

/**
 * Profile of an input processor, can be input method or keyboard layout
 */
interface IInputProcessorProfile {
    /**
     * Locale of the profile
     */
    val locale: Locale

    /**
     * The localized name of the profile
     */
    val name: String
}

/**
 * Profile of an input method
 */
interface IInputMethodProfile : IInputProcessorProfile

/**
 * Profile of keyboard layout
 */
interface IKeyBoardLayout : IInputProcessorProfile

/**
 * Fallback value of [IInputProcessorProfile]
 */
object UnknownInputProcessorProfile : IInputProcessorProfile {
    override val locale: Locale get() = "en"
    override val name: String get() = "[Fallback Value]Unknown Input Processor"
}
