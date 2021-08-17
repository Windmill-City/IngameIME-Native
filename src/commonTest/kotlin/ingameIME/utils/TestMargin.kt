package ingameIME.utils

import kotlin.test.Test
import kotlin.test.assertEquals

class TestMargin {
    @Test
    fun testToMargin() {
        assertEquals(
            Margin(10, 10, 5, 5),
            "10,5".toMargin()
        )
        assertEquals(
            Margin(10, 20, 5, 15),
            "10,20,5,15".toMargin()
        )
    }
}