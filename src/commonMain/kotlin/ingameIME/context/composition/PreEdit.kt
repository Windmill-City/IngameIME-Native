package ingameIME.context.composition

import ingameIME.utils.Margin
import ingameIME.utils.Rect

/**
 * PreEdit - Text to form [CandidateList]
 *
 * @param composition who it belongs to also act as a lock object
 * @param defaultFontHeight fallback height of the bounding box
 * @param margin margin of the bounding box
 */
abstract class PreEdit(val composition: Composition, var defaultFontHeight: Int, margin: Margin) {
    /**
     * Content of the [PreEdit]
     *
     * Application should draw this on screen
     * Application should update [boundingBox] when this change
     */
    val content: String = ""

    /**
     * Selection of the [PreEdit]
     *
     * Application should draw caret or selection of the [content] with it
     */
    val selection: IntRange = IntRange.EMPTY

    /**
     * Margin of the bounding box
     *
     * Make the candidate window not getting too close
     */
    var margin: Margin = margin
        /**
         * If [content] is empty, we make the box no space, for better rendering experience
         */
        get() {
            return if (content.isEmpty()) field.copy(left = 0, right = 0) else field
        }

    /**
     * Bounding box of the [content] drawing on screen
     *
     * Input method(IM) use this to position its candidate list window
     *
     * @Note When [content] is empty, we use [defaultFontHeight] as its height,
     * in case IM 's candidate list overlap the textbox
     */
    var boundingBox: Rect = Rect(0, 0, 0, defaultFontHeight)
        get() = field.copy(
            x = field.x - margin.left,
            y = field.y - margin.top,
            width = field.width + margin.width,
            height = if (content.isEmpty()) defaultFontHeight else field.height + margin.height
        )
}
