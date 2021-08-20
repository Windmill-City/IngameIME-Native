package ingameIME.profile

/**
 * Return the localized name of the locale
 */
actual external fun Locale.getName(): String

class InputProcessorProfile : IInputProcessorProfile {
    /**
     * Locale of the profile
     */
    override val locale: Locale
        external get

    /**
     * The localized name of the profile
     */
    override val name: String
        external get

    /**
     * Set to true to dispose the native handle
     */
    override var disposed: Boolean
        external get
        external set
}