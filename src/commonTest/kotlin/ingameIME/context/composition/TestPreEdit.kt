package ingameIME.context.composition

import ingameIME.utils.BoundingBox
import ingameIME.utils.Margin
import kotlin.test.Test
import kotlin.test.assertFails
import kotlin.test.asserter

class TestPreEdit {
    class TestPreEdit : PreEdit() {
        fun setNotEmptyCtx() {
            context = Context("123", IntRange.EMPTY)
        }

        fun setEmptyCtx() {
            context = Context("", IntRange.EMPTY)
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
        asserter.assertEquals(
            "Not init font height not throws",
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
        asserter.assertEquals(
            "Not init font height not throws",
            "Property defaultFontHeight should be initialized before get.",
            err.message
        )
    }

    @Test
    fun testGetBoundingBoxHasDefaultFontHeightEmptyCtx() {
        preEdit.setEmptyCtx()
        val fontHeight = 10
        preEdit.defaultFontHeight = fontHeight
        asserter.assertEquals(
            "bounding box",
            BoundingBox(0, 0, 0, 10),
            preEdit.boundingBox
        )
    }

    @Test
    fun testGetBoundingBoxHasDefaultFontHeightNotEmptyCtx() {
        preEdit.setNotEmptyCtx()
        preEdit.defaultFontHeight = fontHeight
        asserter.assertEquals(
            "bounding box",
            BoundingBox(0, 0, 0, 0),
            preEdit.boundingBox
        )
    }

    @Test
    fun testGetBoundingBoxEmptyCtxWithMargin() {
        preEdit.defaultFontHeight = fontHeight
        preEdit.margin = margin
        preEdit.setEmptyCtx()
        asserter.assertEquals(
            "bounding box",
            BoundingBox(0, -20, 0, fontHeight + margin.height),
            preEdit.boundingBox
        )
    }

    @Test
    fun testGetBoundingBoxNotEmptyCtxWithMargin() {
        preEdit.defaultFontHeight = fontHeight
        preEdit.margin = margin
        preEdit.setNotEmptyCtx()
        asserter.assertEquals(
            "bounding box",
            BoundingBox(-5, -20, margin.width, margin.height),
            preEdit.boundingBox
        )
    }
}