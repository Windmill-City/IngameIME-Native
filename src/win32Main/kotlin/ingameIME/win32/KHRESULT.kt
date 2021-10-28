package ingameIME.win32

import platform.windows.HRESULT

inline val HRESULT.succeed: Boolean get() = this >= 0

inline val HRESULT.failed: Boolean get() = this < 0

/**
 * Run on success
 */
inline fun HRESULT.ifSucceed(callback: (HRESULT) -> Unit) {
    if (this.succeed) callback(this)
}

/**
 * Run on failure
 */
inline fun HRESULT.ifFailed(callback: (HRESULT) -> Unit) {
    if (this.failed) callback(this)
}

/**
 * Throws when Libtf call failed
 */
class LibtfError(result: HRESULT, message: String?) : RuntimeException("HR=${result}:$message")

/**
 * Throw an exception on failure
 */
inline fun HRESULT.succeedOrThr(message: String?) {
    this.ifFailed { throw LibtfError(this, message) }
}

/**
 * Run if success, otherwise throw an exception
 */
inline fun HRESULT.ifSucceedOrThr(message: String, callback: (HRESULT) -> Unit = {}) {
    this.succeedOrThr(message)
    callback(this)
}