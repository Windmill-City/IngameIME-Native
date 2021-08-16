package ingameIME.win32

import kotlinx.cinterop.*
import platform.windows.BSTR
import platform.windows.BSTRVar
import platform.windows.SysAllocStringLen

/**
 * Convert BSTR to Kotlin String
 */
fun BSTR.toKString(): String {
    memScoped {
        return this@toKString.getPointer(this).reinterpret<ShortVar>().toKStringFromUtf16()
    }
}

/**
 * Convert BSTRVar to Kotlin String
 */
fun BSTRVar.toKString(): String {
    memScoped {
        return this@toKString.value!!.toKString()
    }
}

/**
 * Convert String to BSTR
 */
fun String.toBSTR(): BSTR {
    return SysAllocStringLen(wcstr, length.toUInt()) ?: throw Error("Out of memory")
}
