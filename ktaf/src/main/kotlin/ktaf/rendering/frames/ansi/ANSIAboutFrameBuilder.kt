package ktaf.rendering.frames.ansi

import ktaf.assets.Size
import ktaf.extensions.ensureFinishedSentence
import ktaf.rendering.FramePosition
import ktaf.rendering.frames.AboutFrameBuilder
import ktaf.rendering.frames.Frame

/**
 * Provides a ANSI about frame builder that builds in to the specified [ansiGridStringBuilder].
 */
public class ANSIAboutFrameBuilder(
    private val ansiGridStringBuilder: ANSIGridStringBuilder,
    private val frameSize: Size,
    private val backgroundColor: ANSIColor = ANSIColor.RESET,
    private val borderColor: ANSIColor = ANSIColor.BRIGHT_BLACK,
    private val titleColor: ANSIColor = ANSIColor.WHITE,
    private val nameColor: ANSIColor = ANSIColor.GREEN,
    private val descriptionColor: ANSIColor = ANSIColor.WHITE,
    private val authorColor: ANSIColor = ANSIColor.BRIGHT_BLACK
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
            ansiGridStringBuilder.drawWrapped("Created by $author.", leftMargin, lastPosition.y + 2, availableWidth, authorColor)
        } else {
            ansiGridStringBuilder.drawWrapped("BP.AdventureFramework by Ben Pollard 2011 - 2023.", leftMargin, lastPosition.y + 2, availableWidth, authorColor)
        }

        return ANSIGridTextFrame(ansiGridStringBuilder, 0, 0, backgroundColor).also {
            it.acceptsInput = false
        }
    }
}
