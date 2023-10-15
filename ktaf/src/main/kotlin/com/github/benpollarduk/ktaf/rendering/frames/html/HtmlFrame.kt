package com.github.benpollarduk.ktaf.rendering.frames.html

import com.github.benpollarduk.ktaf.io.RenderFrame
import com.github.benpollarduk.ktaf.rendering.FramePosition
import com.github.benpollarduk.ktaf.rendering.frames.Frame
import com.github.benpollarduk.ktaf.utilities.NEWLINE

/**
 * Provides a HTML based [Frame].
 */
public class HtmlFrame(
    private val pageBuilder: HtmlPageBuilder,
    public override val acceptsInput: Boolean = true
) : Frame {
    override fun render(callback: RenderFrame) {
        callback(toString(), acceptsInput, FramePosition(0, 0))
    }

    override fun toString(): String {
        val stringBuilder = StringBuilder()

        when (pageBuilder.mode) {
            is HtmlElementType.Div -> {
                stringBuilder.append(divOpen)
            }
            is HtmlElementType.Document -> {
                stringBuilder.append("${getHtmlOpen(pageBuilder.mode.title, pageBuilder.mode.css)}$bodyOpen$divOpen")
            }
        }

        stringBuilder.append(pageBuilder.toString())

        when (pageBuilder.mode) {
            is HtmlElementType.Div -> {
                stringBuilder.append(divClose)
            }
            is HtmlElementType.Document -> {
                stringBuilder.append("$divClose$bodyClose$HTML_CLOSE")
            }
        }

        return stringBuilder.toString()
    }

    private fun getHtmlOpen(title: String = "", css: String = ""): String {
        return "<!DOCTYPE html>$NEWLINE<html>$NEWLINE<head>$NEWLINE<title>$title</title>$NEWLINE" +
            "<style>$css</style>$NEWLINE</head>$NEWLINE"
    }

    private companion object {
        private const val HTML_CLOSE: String = "</html>"
        private val bodyOpen: String = "<body>$NEWLINE"
        private val bodyClose: String = "</body>$NEWLINE"
        private val divOpen: String = "<div>$NEWLINE"
        private val divClose: String = "</div>$NEWLINE"
    }
}
