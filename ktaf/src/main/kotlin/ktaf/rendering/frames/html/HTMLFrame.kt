package ktaf.rendering.frames.html

import ktaf.io.DisplayTextOutput
import ktaf.rendering.frames.Frame

/**
 * Provides a HTML based [Frame].
 */
public class HTMLFrame(private val pageBuilder: HTMLPageBuilder) : Frame {
    override var cursorLeft: Int = 0
    override var cursorTop: Int = 0
    override var acceptsInput: Boolean = true

    override fun render(displayTextOutput: DisplayTextOutput) {
        displayTextOutput(toString())
    }

    override fun toString(): String {
        val stringBuilder = StringBuilder()

        when (pageBuilder.mode) {
            is HTMLElementType.Div -> {
                stringBuilder.append(divOpen)
            }
            is HTMLElementType.Document -> {
                stringBuilder.append("${getHtmlOpen(pageBuilder.mode.tile, pageBuilder.mode.css)}$bodyOpen$divOpen")
            }
        }

        stringBuilder.append(pageBuilder.toString())

        when (pageBuilder.mode) {
            is HTMLElementType.Div -> {
                stringBuilder.append(divClose)
            }
            is HTMLElementType.Document -> {
                stringBuilder.append("$divClose$bodyClose$htmlClose")
            }
        }

        return stringBuilder.toString()
    }

    private fun getHtmlOpen(title: String = "", css: String = ""): String {
        return "<!DOCTYPE html><html><head><title>$title</title><style>$css</style></head>"
    }

    private companion object {
        private const val htmlClose: String = "</html>"
        private const val bodyOpen: String = "<body>"
        private const val bodyClose: String = "</body>"
        private const val divOpen: String = "<div>"
        private const val divClose: String = "</div>"
    }
}
