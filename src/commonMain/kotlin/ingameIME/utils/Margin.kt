package ingameIME.utils

/**
 * Define a margin
 */
data class Margin(val left: Int, val right: Int, val top: Int, val bottom: Int) {
    /**
     * Total width of the margin
     */
    val width: Int = left + right

    /**
     * Total height of the margin
     */
    val height: Int = top + bottom
}

/**
 * Convert a string to [Margin]
 *
 * Syntax:
 *  1. A,B -> left & right = A, top & bottom = B
 *  2. A,B,C,D -> left, right, top, bottom
 */
fun String.toMargin(): Margin {
    Regex("""^(\d),(\d)(?:,(\d),(\d))?$""").matchEntire(this).apply {
        if (this == null) throw AssertionError("Invalid margin syntax:$this")
        return Margin(
            this.groupValues[1].toInt(),
            this.groupValues[if (groupValues.size == 5) 2 else 1].toInt(),
            this.groupValues[if (groupValues.size == 5) 3 else 2].toInt(),
            this.groupValues[if (groupValues.size == 5) 4 else 2].toInt()
        )
    }
}