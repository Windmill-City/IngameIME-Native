package ingameIME.context.composition

import ingameIME.utils.BoundingBox
import ingameIME.utils.Margin
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class TestAPreEdit {
    class TestPreEdit : APreEdit() {
        fun setNotEmptyCtx() {
            context.setProperty(Context("123", IntRange.EMPTY))
        }

        fun setEmptyCtx() {
            context.setProperty(Context("", IntRange.EMPTY))
        }
    }

    private val margin = Margin(5, 5, 20, 20)
    private val fontHeight = 10
    private val preEdit = TestPreEdit()

    @Test
    fun testGetBoundingBoxNoDefaultFontHeightEmptyCtx() {
        preEdit.setEmptyCtx()
        val err = assertFails {
            preEdit.boundingBox
        }
        assertEquals(
            "Property defaultFontHeight should be initialized before get.",
            err.message
        )
    }

    @Test
    fun testGetBoundingBoxNoDefaultFontHeightNotEmptyCtx() {
        preEdit.setNotEmptyCtx()
        val err = assertFails {
            preEdit.boundingBox
        }
        assertEquals(
            "Property defaultFontHeight should be initialized before get.",
            err.message
        )
    }

    @Test
    fun testGetBoundingBoxHasDefaultFontHeightEmptyCtx() {
        preEdit.setEmptyCtx()
        val fontHeight = 10
        preEdit.defaultFontHeight = fontHeight
        assertEquals(
            BoundingBox(0, 0, 0, 10),
            preEdit.boundingBox
        )
    }

    @Test
    fun testGetBoundingBoxHasDefaultFontHeightNotEmptyCtx() {
        preEdit.setNotEmptyCtx()
        preEdit.defaultFontHeight = fontHeight
        assertEquals(
            BoundingBox(0, 0, 0, 0),
            preEdit.boundingBox
        )
    }

    @Test
    fun testGetBoundingBoxEmptyCtxWithMargin() {
        preEdit.defaultFontHeight = fontHeight
        preEdit.margin = margin
        preEdit.setEmptyCtx()
        assertEquals(
            BoundingBox(0, -20, 0, fontHeight + margin.height),
            preEdit.boundingBox
        )
    }

    @Test
    fun testGetBoundingBoxNotEmptyCtxWithMargin() {
        preEdit.defaultFontHeight = fontHeight
        preEdit.margin = margin
        preEdit.setNotEmptyCtx()
        assertEquals(
            BoundingBox(-5, -20, margin.width, margin.height),
            preEdit.boundingBox
        )
    }
}