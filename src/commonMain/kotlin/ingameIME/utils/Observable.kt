package ingameIME.utils

import kotlin.properties.ObservableProperty
import kotlin.reflect.KProperty

typealias ChangeCallback<V> = (property: KProperty<*>, oldValue: V, newValue: V) -> Unit

/**
 * Allow custom change callback for the [KProperty]
 *
 * @param _changeCallback Always on callback, should not modify property in case recursive calls
 * @see setCallback
 */
class Observable<V>(initialValue: V, val _changeCallback: ChangeCallback<V> = { _, _, _ -> }) :
    ObservableProperty<V>(initialValue) {
    /**
     * Call after the value has changed
     * @Note You can modify value in it without worries of recursive calls
     */
    var changeCallback: ChangeCallback<V>? = null

    /**
     * Prevent callback when it is handing already
     */
    private var handing = false

    override fun afterChange(property: KProperty<*>, oldValue: V, newValue: V) {
        _changeCallback(property, oldValue, newValue)
        if (!handing) {
            handing = true
            changeCallback?.invoke(property, oldValue, newValue)
            handing = false
        }
    }
}

/**
 * Set callback for [Observable] [KProperty]
 *
 * @sample
 * val field by Observable(val)
 * this::field.setCallback { property, oldValue, newValue ->  }
 */
inline fun KProperty<*>.setCallback(noinline changeCallback: ChangeCallback<Any?>?) {
    if (this is Observable<*>) {
        this.changeCallback = changeCallback
    }
}

/**
 * get callback of [Observable] [KProperty]
 *
 * @sample
 * val field by Observable(val)
 * this::field.getCallback<V>()
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified V> KProperty<*>.getCallback(): ChangeCallback<V>? {
    if (this is Observable<*>) {
        return this.changeCallback as ChangeCallback<V>
    }
    return null
}

/**
 * Check if [Observable] [KProperty] has callback
 *
 * @sample
 * val field by Observable(val)
 * this::field.hasCallback()
 */
inline fun KProperty<*>.hasCallback(): Boolean {
    if (this is Observable<*>) {
        return this.changeCallback != null
    }
    return false
}
