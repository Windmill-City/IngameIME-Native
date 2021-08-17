package ingameIME.win32

import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.toKStringFromUtf8
import platform.posix.snprintf

/**
 * HKL is a keyboard layout handle, so it is Long here
 */
fun Long.toFormattedString(): String {
    memScoped {
        val hklStr = this.allocArray<ByteVar>(9)
        snprintf(
            hklStr,
            9,
            "%08x",
            this@toFormattedString
        )
        return hklStr.toKStringFromUtf8()
    }
}