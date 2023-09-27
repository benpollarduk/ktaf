package ktaf.rendering.frames.html

import ktaf.helpers.newline
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
        return "<!DOCTYPE html>$newline<html>$newline<head>$newline<title>$title</title>$newline" +
            "<style>$css</style>$newline</head>$newline"
    }

    private companion object {
        private val newline = newline()
        private const val htmlClose: String = "</html>"
        private val bodyOpen: String = "<body>$newline"
        private val bodyClose: String = "</body>$newline"
        private val divOpen: String = "<div>$newline"
        private val divClose: String = "</div>$newline"
    }
}
