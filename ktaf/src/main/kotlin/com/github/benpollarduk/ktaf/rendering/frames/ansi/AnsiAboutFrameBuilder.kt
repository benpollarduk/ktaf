package com.github.benpollarduk.ktaf.rendering.frames.ansi

import com.github.benpollarduk.ktaf.assets.Size
import com.github.benpollarduk.ktaf.extensions.ensureFinishedSentence
import com.github.benpollarduk.ktaf.rendering.FramePosition
import com.github.benpollarduk.ktaf.rendering.frames.AboutFrameBuilder
import com.github.benpollarduk.ktaf.rendering.frames.Frame

/**
 * Provides a ANSI about frame builder that builds in to the specified [ansiGridStringBuilder].
 */
public class AnsiAboutFrameBuilder(
    private val ansiGridStringBuilder: AnsiGridStringBuilder,
    private val frameSize: Size,
    private val backgroundColor: AnsiColor = AnsiColor.RESET,
    private val borderColor: AnsiColor = AnsiColor.BRIGHT_BLACK,
    private val titleColor: AnsiColor = AnsiColor.WHITE,
    private val nameColor: AnsiColor = AnsiColor.GREEN,
    private val descriptionColor: AnsiColor = AnsiColor.WHITE,
    private val authorColor: AnsiColor = AnsiColor.BRIGHT_BLACK
) : AboutFrameBuilder {
    override fun build(title: String, description: String, author: String): Frame {
        val availableWidth = frameSize.width - 4
        val leftMargin = 2

        ansiGridStringBuilder.resize(frameSize)
        ansiGridStringBuilder.drawBoundary(borderColor)
        var lastPosition: FramePosition = ansiGridStringBuilder.drawWrapped(
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
        lastPosition = ansiGridStringBuilder.drawWrapped(
            title,
            leftMargin,
            lastPosition.y + 3,
            availableWidth,
            nameColor
        )
        lastPosition = ansiGridStringBuilder.drawWrapped(
            description.ensureFinishedSentence(),
            leftMargin,
            lastPosition.y + 2,
            availableWidth,
            descriptionColor
        )

        if (author.isNotEmpty()) {
            ansiGridStringBuilder.drawWrapped(
                "Created by $author.",
                leftMargin,
                lastPosition.y + 2,
                availableWidth,
                authorColor
            )
        } else {
            ansiGridStringBuilder.drawWrapped(
                "ktaf by Ben Pollard 2023.",
                leftMargin,
                lastPosition.y + 2,
                availableWidth,
                authorColor
            )
        }

        return AnsiGridTextFrame(ansiGridStringBuilder, 0, 0, false, backgroundColor)
    }
}
