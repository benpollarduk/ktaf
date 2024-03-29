package com.github.benpollarduk.ktaf.rendering.frames.ansi

import com.github.benpollarduk.ktaf.io.RenderFrame
import com.github.benpollarduk.ktaf.rendering.FramePosition
import com.github.benpollarduk.ktaf.rendering.frames.Frame
import com.github.benpollarduk.ktaf.utilities.NEWLINE

/**
 * Provides an ANSI grid based [Frame].
 */
public class AnsiGridTextFrame(
    private val builder: AnsiGridStringBuilder,
    private val cursorLeft: Int,
    private val cursorTop: Int,
    public override val acceptsInput: Boolean = true,
    private val backgroundColor: AnsiColor = AnsiColor.BLACK
) : Frame {
    @Suppress("NestedBlockDepth")
    internal fun renderNoColor(stringBuilder: StringBuilder) {
        for (y in 0 until builder.displaySize.height) {
            for (x in 0 until builder.displaySize.width) {
                val c = builder.getCharacter(x, y)

                if (c != 0.toChar()) {
                    stringBuilder.append(c)
                } else {
                    stringBuilder.append(' ')
                }
            }

            // for all but the last line append the newline
            if (y < builder.displaySize.height - 1) {
                stringBuilder.append(NEWLINE)
            }
        }
    }

    @Suppress("NestedBlockDepth")
    internal fun renderColor(stringBuilder: StringBuilder) {
        var fontColor = builder.getCellColor(0, 0)

        // prep by setting background and first font color
        stringBuilder.append("${backgroundColor.toBackgroundColorEscapeCode()}${fontColor.toFontColorEscapeCode()}")

        for (y in 0 until builder.displaySize.height) {
            for (x in 0 until builder.displaySize.width) {
                val c = builder.getCharacter(x, y)

                if (c != 0.toChar()) {
                    val newFontColor = builder.getCellColor(x, y)
                    if (fontColor != newFontColor) {
                        fontColor = newFontColor
                        stringBuilder.append(fontColor.toFontColorEscapeCode())
                    }
                    stringBuilder.append(c)
                } else {
                    stringBuilder.append(' ')
                }
            }

            // for all but the last line append the newline
            if (y < builder.displaySize.height - 1) {
                stringBuilder.append(NEWLINE)
            }
        }
    }

    override fun render(callback: RenderFrame) {
        val stringBuilder = StringBuilder()

        if (isColorSuppressed()) {
            renderNoColor(stringBuilder)
        } else {
            renderColor(stringBuilder)
        }

        // reset both font and background colors
        stringBuilder.append(
            "${AnsiColor.RESET.toFontColorEscapeCode()}${AnsiColor.RESET.toBackgroundColorEscapeCode()}"
        )

        callback(stringBuilder.toString(), acceptsInput, FramePosition(cursorLeft, cursorTop))
    }

    override fun toString(): String {
        val stringBuilder = StringBuilder()
        val newLine = NEWLINE

        for (y in 0 until builder.displaySize.height) {
            for (x in 0 until builder.displaySize.width) {
                stringBuilder.append(builder.getCharacter(x, y))
            }

            // for all but the last line append the newline
            if (y < builder.displaySize.height - 1) {
                stringBuilder.append(newLine)
            }
        }

        return stringBuilder.toString()
    }

    private companion object {
        private const val NO_COLOR: String = "NO_COLOR"

        private fun isColorSuppressed(): Boolean {
            // terminal color may be suppressed with the NO_COLOR environment variable
            return when (System.getenv(NO_COLOR)?.lowercase() ?: "") {
                "" -> false
                "0" -> false
                "false" -> false
                else -> true
            }
        }
    }
}
