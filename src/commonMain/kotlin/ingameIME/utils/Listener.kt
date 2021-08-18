package ingameIME.utils

/**
 * Listen for property changes
 *
 * @param T property's type
 */
fun interface IListener<T> {
    /**
     * Called when the property is assigned
     *
     * @param old old property
     * @param new new property
     * @return The value actually assigned
     */
    fun onChange(old: T, new: T): T
}

/**
 * Container of listeners
 */
open class ListenerHolder<T> : IListener<T> {
    private val listeners = arrayListOf<IListener<T>>()

    /**
     * Called when the property is assigned
     *
     * @param old old property
     * @param new new property
     * @return The value actually assigned
     */
    @Suppress("NAME_SHADOWING")
    override fun onChange(old: T, new: T): T {
        var new = new
        listeners.forEach { new = it.onChange(old, new) }
        return new
    }

    /**
     * Append to the end of the listener list
     *
     * @param listener the listener to add
     * @return true if the listener has been added
     *         false if it already in the list
     */
    fun append(listener: IListener<T>): Boolean {
        if (listeners.contains(listener)) return false
        listeners.add(listener)
        return true
    }

    /**
     * Insert at the specified index of the listener list
     *
     * @param index insertion point
     * @param listener the listener to add
     * @return true if the listener has been added
     *         false if it already in the list
     */
    fun insertAt(index: Int, listener: IListener<T>): Boolean {
        if (listeners.contains(listener)) return false
        listeners.add(index, listener)
        return true
    }

    /**
     * Remove from the listener list
     *
     * @param listener the listener to remove
     * @return true if the listener has been removed
     *         false if it was not in the list
     */
    fun remove(listener: IListener<T>): Boolean {
        return listeners.remove(listener)
    }

    /**
     * @return index of specific listener in the list or -1 if not exist
     */
    fun indexOf(listener: IListener<T>): Int {
        return listeners.indexOf(listener)
    }

    /**
     * Check if a specific listener is included in the listener list
     *
     * @param listener to check
     * @return true if it contains, false otherwise
     */
    fun hasListener(listener: IListener<T>): Boolean {
        return listeners.contains(listener)
    }
}