package utils

import ingameIME.utils.Margin
import ingameIME.utils.toMargin
import kotlin.test.Test
import kotlin.test.asserter

class TestMargin {
    @Test
    fun testToMargin() {
        asserter.assertEquals(
            "Syntax A,B",
            Margin(10, 10, 5, 5),
            "10,5".toMargin()
        )
        asserter.assertEquals(
            "Syntax A,B,C,D",
            Margin(10, 20, 5, 15),
            "10,20,5,15".toMargin()
        )
    }
}