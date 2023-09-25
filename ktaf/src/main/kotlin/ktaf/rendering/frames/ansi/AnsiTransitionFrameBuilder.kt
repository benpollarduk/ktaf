package ktaf.rendering.frames.ansi

import ktaf.extensions.ensureFinishedSentence
import ktaf.logic.Game
import ktaf.rendering.FramePosition
import ktaf.rendering.frames.Frame
import ktaf.rendering.frames.TransitionFrameBuilder

/**
 * Provides an ANSI title frame builder that builds in to the specified [ansiGridStringBuilder].
 */
public class AnsiTransitionFrameBuilder(
    private val ansiGridStringBuilder: AnsiGridStringBuilder,
    private val backgroundColor: AnsiColor = AnsiColor.RESET,
    private val borderColor: AnsiColor = AnsiColor.BRIGHT_BLACK,
    private val titleColor: AnsiColor = AnsiColor.GREEN,
    private val messageColor: AnsiColor = AnsiColor.YELLOW
) : TransitionFrameBuilder {
    override fun build(title: String, message: String, game: Game): Frame {
        val availableWidth = game.frameSize.width - 4
        val leftMargin = 2
        var lastPosition = FramePosition(0, 2)

        ansiGridStringBuilder.resize(game.frameSize)
        ansiGridStringBuilder.drawBoundary(borderColor)

        if (title.isNotEmpty()) {
            lastPosition = ansiGridStringBuilder.drawWrapped(title, leftMargin, lastPosition.y, availableWidth, titleColor)
            ansiGridStringBuilder.drawUnderline(leftMargin, lastPosition.y + 1, title.length, titleColor)
            lastPosition = FramePosition(lastPosition.x, lastPosition.y + 3)
        }

        ansiGridStringBuilder.drawWrapped(message.ensureFinishedSentence(), leftMargin, lastPosition.y, availableWidth, messageColor)

        return AnsiGridTextFrame(ansiGridStringBuilder, 0, 0, backgroundColor).also {
            it.acceptsInput = false
        }
    }
}
