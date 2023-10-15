package com.github.benpollarduk.ktaf.rendering.frames.ansi

import com.github.benpollarduk.ktaf.assets.Size
import com.github.benpollarduk.ktaf.rendering.FramePosition
import com.github.benpollarduk.ktaf.utilities.NEWLINE
import com.github.benpollarduk.ktaf.utilities.StringUtilities

/**
 * Provides a class for building strings as part of a grid.
 */
public class AnsiGridStringBuilder(
    public var leftBoundary: Char = '|',
    public var rightBoundary: Char = '|',
    public var horizontalDivider: Char = '-',
    public var lineTerminator: String = NEWLINE
) {
    private var buffer: Array<Array<Char>> = Array(0) { Array(0) { ' ' } }
    private var colors: Array<Array<AnsiColor>> = Array(0) { Array(0) { AnsiColor.WHITE } }

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
        colors = Array(displaySize.width) { Array(displaySize.height) { AnsiColor.WHITE } }
        flush()
    }

    /**
     * Get a character from the buffer at a specified [x] and [y] location.
     */
    public fun getCharacter(x: Int, y: Int): Char {
        return buffer[x][y]
    }

    /**
     * Get a cell [AnsiColor] at a specified [x] and [y] location.
     */
    public fun getCellColor(x: Int, y: Int): AnsiColor {
        return colors[x][y]
    }

    /**
     * Set the cell at  a specified [x] and [y] position to a specified [character] and [color].
     */
    public fun setCell(x: Int, y: Int, character: Char, color: AnsiColor) {
        buffer[x][y] = character
        colors[x][y] = color
    }

    /**
     * Get the number of lines that a string will take up in a given area.
     */
    public fun getNumberOfLines(value: String, startY: Int, maxWidth: Int): Int {
        var endY = startY
        var copy = String(value.toCharArray())

        while (copy.any()) {
            val extractedString = StringUtilities.cutLineFromParagraph(copy, maxWidth)
            copy = extractedString.rest

            if (copy.trim().any()) {
                endY++
            }
        }

        return (endY - startY) + 1
    }

    /**
     * Draw a boundary with a specified [color].
     */
    public fun drawBoundary(color: AnsiColor) {
        drawHorizontalDivider(0, color)
        drawHorizontalDivider(displaySize.height - 1, color)

        for (i in 0 until displaySize.height) {
            setCell(0, i, leftBoundary, color)
            setCell(displaySize.width - 1, i, rightBoundary, color)
        }
    }

    /**
     * Draw a horizontal divider at a specified [y] position in a specified [color].
     */
    public fun drawHorizontalDivider(y: Int, color: AnsiColor) {
        for (i in 1 until displaySize.width - 1) {
            setCell(i, y, horizontalDivider, color)
        }
    }

    /**
     * Draw a wrapped string at a specified [startX] and [startY] position with a [maxWidth]. The end position will be
     * returned.
     */
    public fun drawWrapped(value: String, startX: Int, startY: Int, maxWidth: Int, color: AnsiColor): FramePosition {
        var paragraph = value
        var endX = startX
        var endY = startY
        while (paragraph.isNotEmpty()) {
            val extractedString = StringUtilities.cutLineFromParagraph(paragraph, maxWidth - startX)
            paragraph = extractedString.rest

            for (i in 0 until extractedString.extractedWord.length) {
                endX = startX + i
                setCell(endX, endY, extractedString.extractedWord[i], color)
            }

            if (paragraph.trim().isNotEmpty()) {
                endY++
            }
        }

        return FramePosition(endX, endY)
    }

    /**
     * Draw a centralised wrapped string at a specified [startY] position with a [maxWidth]. The end position will be
     * returned.
     */
    public fun drawCentralisedWrapped(value: String, startY: Int, maxWidth: Int, color: AnsiColor): FramePosition {
        var paragraph = value
        var endX = 0
        var endY = startY
        while (paragraph.isNotEmpty()) {
            val extractedString = StringUtilities.cutLineFromParagraph(paragraph, maxWidth)
            paragraph = extractedString.rest
            val startX = (maxWidth / 2) - (extractedString.extractedWord.length / 2)

            for (i in 0 until extractedString.extractedWord.length) {
                endX = startX + i
                setCell(endX, endY, extractedString.extractedWord[i], color)
            }

            if (paragraph.trim().isNotEmpty()) {
                endY++
            }
        }

        return FramePosition(endX, endY)
    }

    /**
     * Draw an underline at a specified [x] and [y] location for a specified [length] in a specified [color].
     */
    public fun drawUnderline(x: Int, y: Int, length: Int, color: AnsiColor) {
        val char = '-'
        for (i in 0 until length) {
            setCell(x + i, y, char, color)
        }
    }

    /**
     * Flush the buffer.
     */
    public fun flush() {
        buffer = Array(displaySize.width) { Array(displaySize.height) { ' ' } }
    }

    override fun toString(): String {
        val stringBuilder = StringBuilder()
        val newLine = NEWLINE

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
