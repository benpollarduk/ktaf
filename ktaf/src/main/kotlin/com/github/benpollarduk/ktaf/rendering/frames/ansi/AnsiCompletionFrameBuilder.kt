package com.github.benpollarduk.ktaf.rendering.frames.ansi

import com.github.benpollarduk.ktaf.assets.Size
import com.github.benpollarduk.ktaf.extensions.ensureFinishedSentence
import com.github.benpollarduk.ktaf.rendering.FramePosition
import com.github.benpollarduk.ktaf.rendering.frames.CompletionFrameBuilder
import com.github.benpollarduk.ktaf.rendering.frames.Frame

/**
 * Provides an ANSI completion frame builder that builds in to the specified [ansiGridStringBuilder].
 */
public class AnsiCompletionFrameBuilder(
    private val ansiGridStringBuilder: AnsiGridStringBuilder,
    private val frameSize: Size,
    private val backgroundColor: AnsiColor = AnsiColor.RESET,
    private val borderColor: AnsiColor = AnsiColor.BRIGHT_BLACK,
    private val titleColor: AnsiColor = AnsiColor.GREEN,
    private val descriptionColor: AnsiColor = AnsiColor.WHITE
) : CompletionFrameBuilder {
    override fun build(title: String, reason: String): Frame {
        val availableWidth = frameSize.width - 4
        val leftMargin = 2

        ansiGridStringBuilder.resize(frameSize)
        ansiGridStringBuilder.drawBoundary(borderColor)
        val lastPosition: FramePosition = ansiGridStringBuilder.drawWrapped(
            title,
            leftMargin,
            2,
            availableWidth,
            titleColor
        )
        ansiGridStringBuilder.drawUnderline(
            leftMargin,
            lastPosition.y + 1,
            title.length,
            titleColor
        )
        ansiGridStringBuilder.drawWrapped(
            reason.ensureFinishedSentence(),
            leftMargin,
            lastPosition.y + 3,
            availableWidth,
            descriptionColor
        )

        return AnsiGridTextFrame(ansiGridStringBuilder, 0, 0, false, backgroundColor)
    }
}
