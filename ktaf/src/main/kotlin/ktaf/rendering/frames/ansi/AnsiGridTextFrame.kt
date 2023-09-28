package ktaf.rendering.frames.ansi

import ktaf.helpers.newline
import ktaf.io.RenderFrame
import ktaf.rendering.FramePosition
import ktaf.rendering.frames.Frame

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
    override fun render(callback: RenderFrame) {
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
        stringBuilder.append(
            "${AnsiColor.RESET.toFontColorEscapeCode()}${AnsiColor.RESET.toBackgroundColorEscapeCode()}"
        )
        callback(stringBuilder.toString(), acceptsInput, FramePosition(cursorLeft, cursorTop))
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
