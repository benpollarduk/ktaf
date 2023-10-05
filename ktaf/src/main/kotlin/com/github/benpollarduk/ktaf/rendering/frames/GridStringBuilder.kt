package com.github.benpollarduk.ktaf.rendering.frames

import com.github.benpollarduk.ktaf.assets.Size
import com.github.benpollarduk.ktaf.helpers.newline
import com.github.benpollarduk.ktaf.rendering.FramePosition
import com.github.benpollarduk.ktaf.utilities.StringUtilities

/**
 * Provides a class for building strings as part of a grid.
 */
public class GridStringBuilder(
    public var leftBoundary: Char = '|',
    public var rightBoundary: Char = '|',
    public var horizontalDivider: Char = '-',
    public var lineTerminator: String = newline()
) {
    private var buffer: Array<Array<Char>> = Array(0) { Array(0) { ' ' } }

    /**
     * Get the size of the display area.
     */
    public var displaySize: Size = Size(0, 0)
        private set

    /**
     * Resize the display area.
     */
    public fun resize(displaySize: Size) {
        this.displaySize = displaySize
        flush()
    }

    /**
     * Get a character from the buffer at a specified [x] and [y] location.
     */
    public fun getCharacter(x: Int, y: Int): Char {
        return buffer[x][y]
    }

    /**
     * Set the cell at  a specified [x] and [y] position to a specified [character].
     */
    public fun setCell(x: Int, y: Int, character: Char) {
        buffer[x][y] = character
    }

    /**
     * Draw a wrapped string at a specified [startX] and [startY] position with a [maxWidth]. The end position will be
     * returned.
     */
    public fun drawWrapped(value: String, startX: Int, startY: Int, maxWidth: Int): FramePosition {
        var paragraph = value
        var endX = startX
        var endY = startY
        while (paragraph.isNotEmpty()) {
            val extractedString = StringUtilities.cutLineFromParagraph(paragraph, maxWidth - startX)
            paragraph = extractedString.rest

            for (i in 0 until extractedString.extractedWord.length) {
                endX = startX + i
                setCell(endX, endY, extractedString.extractedWord[i])
            }

            if (paragraph.trim().isNotEmpty()) {
                endY++
            }
        }

        return FramePosition(endX, endY)
    }

    /**
     * Flush the buffer.
     */
    public fun flush() {
        buffer = Array(displaySize.width) { Array(displaySize.height) { ' ' } }
    }

    override fun toString(): String {
        val stringBuilder = StringBuilder()
        val newLine = newline()

        for (y in 0 until displaySize.height) {
            for (x in 0 until displaySize.width) {
                stringBuilder.append(getCharacter(x, y))
            }

            // for all but the last line append the newline
            if (y < displaySize.height - 1) {
                stringBuilder.append(newLine)
            }
        }

        return stringBuilder.toString()
    }
}
