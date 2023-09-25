package ktaf.rendering.frames.ansi

import ktaf.helpers.newline
import ktaf.io.DisplayTextOutput
import ktaf.rendering.frames.Frame

/**
 * Provides an ANSI grid based [Frame].
 */
public class AnsiGridTextFrame(
    private val builder: AnsiGridStringBuilder,
    override var cursorLeft: Int,
    override var cursorTop: Int,
    private var backgroundColor: AnsiColor = AnsiColor.BLACK
) : Frame {
    override var acceptsInput: Boolean = true

    override fun render(displayTextOutput: DisplayTextOutput) {
        val stringBuilder = StringBuilder()
        val newLine = newline()
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
                stringBuilder.append(newLine)
            }
        }

        // reset both font and background colors
        stringBuilder.append("${AnsiColor.RESET.toFontColorEscapeCode()}${AnsiColor.RESET.toBackgroundColorEscapeCode()}")
        displayTextOutput(stringBuilder.toString())
    }

    override fun toString(): String {
        val stringBuilder = StringBuilder()
        val newLine = newline()

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
}
