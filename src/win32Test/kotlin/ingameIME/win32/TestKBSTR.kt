package ingameIME.win32

import kotlinx.cinterop.wcstr
import platform.windows.SysAllocString
import kotlin.test.Test
import kotlin.test.assertEquals

class TestKBSTR {
    @Test
    fun testBSTR2KStr() {
        val bstr = SysAllocString("String".wcstr)!!
        assertEquals("String", bstr.toKString())
    }

    @Test
    fun testKStr2BSTR() {
        "String".withBSTR {
            assertEquals("String", it.toKString(false))
        }
    }
}