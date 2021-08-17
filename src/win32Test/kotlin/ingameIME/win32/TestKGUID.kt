package ingameIME.win32

import kotlinx.cinterop.*
import platform.posix.CLSID
import platform.posix.GUID
import platform.posix.memcpy_s
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TestKGUID {
    private val clsid: CLSID = nativeHeap.alloc()

    init {
        //CLSID of Microsoft Pinyin
        clsid.Data1 = 0x81d4e9c9.toUInt()
        clsid.Data2 = 0x1d3b.toUShort()
        clsid.Data3 = 0x41bc.toUShort()
        clsid.Data4[0] = 0x9e.toUByte()
        clsid.Data4[1] = 0x6c.toUByte()
        clsid.Data4[2] = 0x4b.toUByte()
        clsid.Data4[3] = 0x40.toUByte()
        clsid.Data4[4] = 0xbf.toUByte()
        clsid.Data4[5] = 0x79.toUByte()
        clsid.Data4[6] = 0xe3.toUByte()
        clsid.Data4[7] = 0x5e.toUByte()
    }

    @Test
    fun testFormattedStr() {
        assertEquals("{81d4e9c9-1d3b-41bc-9e6c-4b40bf79e35e}", clsid.toFormattedString())
    }

    @Test
    fun testEqual() {
        assertTrue { clsid.isEqual(clsid) }
        memScoped {
            val guid: GUID = this.alloc()
            assertTrue { !(clsid.isEqual(guid)) }
            memcpy_s(guid.ptr, sizeOf<GUID>().toULong(), clsid.ptr, sizeOf<GUID>().toULong())
            assertTrue { clsid.isEqual(guid) }
        }
    }

    @Test
    fun testHashCode() {
        assertEquals(-672159701, clsid.getHashCode())
    }
}