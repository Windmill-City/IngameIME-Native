package ingameIME.utils

/**
 * Listen for property set
 */
interface IListener<T> {
    /**
     * Call on value change
     *
     * @param old old value
     * @param new new value
     * @return value to assign
     */
    fun onChange(old: T, new: T): T
}


class ListenerHolder<T> : IListener<T> {
    private val listeners = arrayListOf<IListener<T>>()

    /**
     * Call on value change
     *
     * @param old old value
     * @param new new value
     * @return value to assign
     */
    @Suppress("NAME_SHADOWING")
    override fun onChange(old: T, new: T): T {
        var new = new
        listeners.forEach { new = it.onChange(old, new) }
        return new
    }

    /**
     * Add a new listener at the end
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
     * Add a new listener at the specified index
     *
     * @param index insert index
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
     * Remove listener
     *
     * @param listener the listener to remove
     * @return true if the listener has been removed
     *         false if it was not in the list
     */
    fun remove(listener: IListener<T>): Boolean {
        return listeners.remove(listener)
    }

    /**
     * @return index of specific listener or -1 if not exist
     */
    fun indexOf(listener: IListener<T>): Int {
        return listeners.indexOf(listener)
    }

    /**
     * Check if specified listener in the list
     *
     * @param listener the listener to check
     * @return true if in else false
     */
    fun hasListener(listener: IListener<T>): Boolean {
        return listeners.contains(listener)
    }
}