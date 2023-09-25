package ktaf.rendering.frames.ansi

import ktaf.extensions.ensureFinishedSentence
import ktaf.logic.Game
import ktaf.rendering.FramePosition
import ktaf.rendering.frames.Frame
import ktaf.rendering.frames.TitleFrameBuilder

/**
 * Provides an ANSI title frame builder that builds in to the specified [ansiGridStringBuilder].
 */
public class AnsiTitleFrameBuilder(
    private val ansiGridStringBuilder: AnsiGridStringBuilder,
    private val backgroundColor: AnsiColor = AnsiColor.RESET,
    private val borderColor: AnsiColor = AnsiColor.BRIGHT_BLACK,
    private val titleColor: AnsiColor = AnsiColor.WHITE,
    private val descriptionColor: AnsiColor = AnsiColor.WHITE
) : TitleFrameBuilder {
    override fun build(game: Game): Frame {
        val availableWidth = game.frameSize.width - 4
        val leftMargin = 2

        ansiGridStringBuilder.resize(game.frameSize)
        ansiGridStringBuilder.drawBoundary(borderColor)
        val lastPosition: FramePosition = ansiGridStringBuilder.drawWrapped(game.name, leftMargin, 2, availableWidth, titleColor)
        ansiGridStringBuilder.drawUnderline(leftMargin, lastPosition.y + 1, game.name.length, titleColor)
        ansiGridStringBuilder.drawWrapped(game.description.ensureFinishedSentence(), leftMargin, lastPosition.y + 3, availableWidth, descriptionColor)

        return AnsiGridTextFrame(ansiGridStringBuilder, 0, 0, backgroundColor).also {
            it.acceptsInput = false
        }
    }
}
