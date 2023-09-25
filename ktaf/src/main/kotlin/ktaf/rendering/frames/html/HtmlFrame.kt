package ktaf.rendering.frames.html

import ktaf.io.DisplayTextOutput
import ktaf.rendering.frames.Frame

/**
 * Provides a HTML based [Frame]. Specify the build mode with the [mode] property.
 */
public class HtmlFrame(
    private val pageBuilder: HtmlPageBuilder,
    private var mode: HtmlBuildMode = HtmlBuildMode.BODY
) : Frame {
    override var cursorLeft: Int = 0
    override var cursorTop: Int = 0
    override var acceptsInput: Boolean = true

    override fun render(displayTextOutput: DisplayTextOutput) {
        displayTextOutput(toString())
    }

    override fun toString(): String {
        val stringBuilder = StringBuilder()

        when (mode) {
            HtmlBuildMode.PAGE -> {
                stringBuilder.append(htmlOpen)
            }
            HtmlBuildMode.BODY -> {
                stringBuilder.append("$htmlOpen$bodyOpen")
            }
        }

        stringBuilder.append(pageBuilder.toString())

        when (mode) {
            HtmlBuildMode.PAGE -> {
                stringBuilder.append(htmlClose)
            }
            HtmlBuildMode.BODY -> {
                stringBuilder.append("$bodyClose$htmlClose")
            }
        }

        return stringBuilder.toString()
    }

    private companion object {
        private val htmlOpen: String = "<html lang=\"en-GB\">" +
            "<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">" +
            "<title>ktaf Frame</title></head>"
        private val htmlClose: String = "</head>"
        private val bodyOpen: String = "<body>"
        private val bodyClose: String = "</body>"
    }
}
