package ingameIME.context.composition

import ingameIME.utils.BoundingBox
import ingameIME.utils.Margin
import kotlin.properties.Delegates

/**
 * PreEdit - Text to form [CandidateList]
 */
@Suppress("MemberVisibilityCanBePrivate")
abstract class PreEdit {
    data class Context(
        /**
         * Content of the [PreEdit]
         *
         * Application should draw this on screen
         * Application should update [boundingBox] when this change
         */
        val content: String,

        /**
         * Selection of the [PreEdit]
         *
         * Application should draw caret or selection of the [content] with it
         */
        val selection: IntRange
    ) {
        /**
         * If it has empty [content]
         */
        fun isEmpty(): Boolean = content.isEmpty()

        /**
         * If [content] not empty
         */
        fun isNotEmpty(): Boolean = content.isNotEmpty()
    }

    /**
     * Changes by input method
     */
    var context: Context = Context("", IntRange.EMPTY)
        protected set

    /**
     * Fallback height of the bounding box
     */
    var defaultFontHeight by Delegates.notNull<Int>()

    /**
     * Margin of the bounding box
     *
     * Make the candidate window not getting too close
     */
    var margin: Margin = Margin(0, 0, 0, 0)
        /**
         * If [context] is empty, we make the box no space, for better visual effect
         */
        get() {
            return if (context.isEmpty()) field.copy(left = 0, right = 0) else field
        }

    /**
     * Bounding box of the [context] drawing on screen
     *
     * Input method(IM) use this to position its candidate list window
     *
     * @Note When [context] is empty, we use [defaultFontHeight] as its height,
     * in case IM 's candidate list overlap the textbox
     *
     * @Note In screen position
     */
    var boundingBox: BoundingBox = BoundingBox(0, 0, 0, 0)
        get() = field.copy(
            x = field.x - margin.left,
            y = field.y - margin.top,
            width = field.width + margin.width,
            height = if (context.isEmpty()) defaultFontHeight else field.height + margin.height
        )
}
