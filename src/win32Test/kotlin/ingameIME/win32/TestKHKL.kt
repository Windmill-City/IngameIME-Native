package ingameIME.win32

import kotlin.test.Test
import kotlin.test.assertEquals

class TestKHKL {
    @Test
    fun testToFormattedStr() {
        assertEquals("003e6a29", 0x3E_6A_29.toLong().toFormattedString())
    }
}