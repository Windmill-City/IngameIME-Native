package ingameIME.win32

import kotlinx.cinterop.*
import platform.posix.GUID
import platform.posix.int32_tVar
import platform.posix.int64_tVar
import platform.posix.snprintf

/**
 * Convert GUID to String which is 32 hex values separated by hyphens, enclosed in braces
 * ex:{00000000-0000-0000-0000-000000000000}
 */
fun GUID.toFormattedString(): String {
    memScoped {
        val guidString = this.allocArray<ByteVar>(39)
        snprintf(
            guidString,
            39,
            "{%08x-%04x-%04x-%04x-%04x%08x}",
            this@toFormattedString.Data1,
            this@toFormattedString.Data2,
            this@toFormattedString.Data3,
            this@toFormattedString.Data4.toLong().toCPointer<int32_tVar>(),
            (this@toFormattedString.Data4.toLong() + sizeOf<int32_tVar>()).toCPointer<int32_tVar>(),
            (this@toFormattedString.Data4.toLong() + sizeOf<int64_tVar>()).toCPointer<int64_tVar>()
        )
        return guidString.toKStringFromUtf8()
    }
}