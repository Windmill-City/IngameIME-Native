package ingameIME.win32

import kotlinx.cinterop.*
import platform.windows.BSTR
import platform.windows.BSTRVar
import platform.windows.SysAllocStringLen
import platform.windows.SysFreeString

/**
 * Free the BSTR
 */
inline fun BSTR.free() {
    SysFreeString(this)
}

/**
 * Convert BSTR to Kotlin String
 */
fun BSTR.toKString(autoFree: Boolean = true): String {
    memScoped {
        with(this@toKString.getPointer(this)) {
            val result = reinterpret<ShortVar>().toKStringFromUtf16()
            if (autoFree) this@toKString.free()
            return result
        }
    }
}

/**
 * Convert BSTRVar to Kotlin String
 */
fun BSTRVar.toKString(autoFree: Boolean = true): String {
    return this@toKString.value!!.toKString(autoFree)
}

/**
 * Convert String to BSTR for use, and release it automatically after use
 */
inline fun String.withBSTR(action: (bstr: BSTR) -> Unit) {
    val bstr = SysAllocStringLen(wcstr, length.toUInt()) ?: throw Error("Out of memory")
    action(bstr)
    bstr.free()
}
