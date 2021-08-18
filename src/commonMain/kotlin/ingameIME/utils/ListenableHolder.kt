package ingameIME.utils

/**
 * Property holder of listenable field
 */
open class ListenableHolder<T>(initialValue: T) : ListenerHolder<T>() {
    private var content: T = initialValue

    /**
     * Set new value to the property
     */
    open fun setProperty(newValue: T) {
        setPropertyInternal(onChange(content, newValue))
    }

    /**
     * Set value without triggering callback
     */
    fun setPropertyInternal(newValue: T) {
        content = newValue
    }

    /**
     * Get current value of the property
     */
    fun getProperty(): T {
        return content
    }
}