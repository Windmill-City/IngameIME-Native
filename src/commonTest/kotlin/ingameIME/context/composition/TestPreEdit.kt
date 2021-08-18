package ingameIME.context.composition

import ingameIME.utils.BoundingBox
import ingameIME.utils.Margin
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class TestAPreEdit {
    private val margin = Margin(5, 5, 20, 20)
    private val fontHeight = 10
    private val preEdit = PreEdit()

    private fun setNotEmptyCtx() {
        preEdit.context.setProperty(PreEdit.Context("123", IntRange.EMPTY))
    }

    private fun setEmptyCtx() {
        preEdit.context.setProperty(PreEdit.Context("", IntRange.EMPTY))
    }

    @Test
    fun testGetBoundingBoxNoDefaultFontHeightEmptyCtx() {
        setEmptyCtx()
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
        setNotEmptyCtx()
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
        setEmptyCtx()
        val fontHeight = 10
        preEdit.defaultFontHeight = fontHeight
        assertEquals(
            BoundingBox(0, 0, 0, 10),
            preEdit.boundingBox
        )
    }

    @Test
    fun testGetBoundingBoxHasDefaultFontHeightNotEmptyCtx() {
        setNotEmptyCtx()
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
        setEmptyCtx()
        assertEquals(
            BoundingBox(0, -20, 0, fontHeight + margin.height),
            preEdit.boundingBox
        )
    }

    @Test
    fun testGetBoundingBoxNotEmptyCtxWithMargin() {
        preEdit.defaultFontHeight = fontHeight
        preEdit.margin = margin
        setNotEmptyCtx()
        assertEquals(
            BoundingBox(-5, -20, margin.width, margin.height),
            preEdit.boundingBox
        )
    }
}