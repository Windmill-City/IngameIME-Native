package ingameIME.win32

import kotlinx.cinterop.*
import platform.posix.GUID
import platform.posix.memcmp
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
            "{%08x-%04x-%04x-%02x%02x-%02x%02x%02x%02x%02x%02x}",
            this@toFormattedString.Data1,
            this@toFormattedString.Data2,
            this@toFormattedString.Data3,
            this@toFormattedString.Data4[0],
            this@toFormattedString.Data4[1],
            this@toFormattedString.Data4[2],
            this@toFormattedString.Data4[3],
            this@toFormattedString.Data4[4],
            this@toFormattedString.Data4[5],
            this@toFormattedString.Data4[6],
            this@toFormattedString.Data4[7],
        )
        return guidString.toKStringFromUtf8()
    }
}

/**
 * Compare if two guid have same content
 */
fun GUID.isEqual(guid: GUID): Boolean {
    return memcmp(this.ptr, guid.ptr, sizeOf<GUID>().toULong()) == 0
}

/**
 * Guid.GetHashCode() of .NET
 */
fun GUID.getHashCode(): Int {
    return this.Data1.toInt() xor (this.Data2.toInt() shl 16 or this.Data3.toInt()) xor (this.Data4[2].toInt() shl 24 or this.Data4[7].toInt())
}