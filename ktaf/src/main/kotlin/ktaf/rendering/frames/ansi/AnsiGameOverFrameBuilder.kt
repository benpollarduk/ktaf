package ktaf.rendering.frames.ansi

import ktaf.assets.Size
import ktaf.extensions.ensureFinishedSentence
import ktaf.rendering.FramePosition
import ktaf.rendering.frames.Frame
import ktaf.rendering.frames.GameOverFrameBuilder

/**
 * Provides an ANSI game over frame builder that builds in to the specified [ansiGridStringBuilder].
 */
public class AnsiGameOverFrameBuilder(
    private val ansiGridStringBuilder: AnsiGridStringBuilder,
    private val backgroundColor: AnsiColor = AnsiColor.RESET,
    private val borderColor: AnsiColor = AnsiColor.BRIGHT_BLACK,
    private val titleColor: AnsiColor = AnsiColor.RED,
    private val descriptionColor: AnsiColor = AnsiColor.WHITE
) : GameOverFrameBuilder {
    override fun build(title: String, reason: String, width: Int, height: Int): Frame {
        val availableWidth = width - 4
        val leftMargin = 2

        ansiGridStringBuilder.resize(Size(width, height))
        ansiGridStringBuilder.drawBoundary(borderColor)
        val lastPosition: FramePosition = ansiGridStringBuilder.drawWrapped(title, leftMargin, 2, availableWidth, titleColor)
        ansiGridStringBuilder.drawUnderline(leftMargin, lastPosition.y + 1, title.length, titleColor)
        ansiGridStringBuilder.drawWrapped(reason.ensureFinishedSentence(), leftMargin, lastPosition.y + 3, availableWidth, descriptionColor)

        return AnsiGridTextFrame(ansiGridStringBuilder, 0, 0, backgroundColor).also {
            it.acceptsInput = false
        }
    }
}
