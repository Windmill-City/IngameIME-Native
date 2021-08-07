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
    Regex("""^(\d+),(\d+)(?:,(\d+),(\d+))?$""").matchEntire(this).apply {
        if (this == null) throw AssertionError("Invalid margin syntax:${this@toMargin}")
        val syntaxSecond = groupValues[3].isNotEmpty()
        return Margin(
            groupValues[1].toInt(),
            groupValues[if (syntaxSecond) 2 else 1].toInt(),
            groupValues[if (syntaxSecond) 3 else 2].toInt(),
            groupValues[if (syntaxSecond) 4 else 2].toInt()
        )
    }
}