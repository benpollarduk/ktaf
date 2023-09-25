package ktaf.rendering.frames.ansi

import ktaf.extensions.ensureFinishedSentence
import ktaf.interpretation.CommandHelp
import ktaf.logic.Game
import ktaf.rendering.FramePosition
import ktaf.rendering.frames.Frame
import ktaf.rendering.frames.HelpFrameBuilder

/**
 * Provides an ANSI help frame builder that builds in to the specified [ansiGridStringBuilder].
 */
public class AnsiHelpFrameBuilder(
    private val ansiGridStringBuilder: AnsiGridStringBuilder,
    private val backgroundColor: AnsiColor = AnsiColor.RESET,
    private val borderColor: AnsiColor = AnsiColor.BRIGHT_BLACK,
    private val titleColor: AnsiColor = AnsiColor.WHITE,
    private val descriptionColor: AnsiColor = AnsiColor.WHITE,
    private val commandColor: AnsiColor = AnsiColor.GREEN,
    private val commandDescriptionColor: AnsiColor = AnsiColor.YELLOW
) : HelpFrameBuilder {
    override fun build(title: String, description: String, commands: List<CommandHelp>, game: Game): Frame {
        val availableWidth = game.frameSize.width - 4
        val leftMargin = 2
        val padding = (if (commands.any()) commands.maxOf { it.command.length } else 0) + 1

        ansiGridStringBuilder.resize(game.frameSize)
        ansiGridStringBuilder.drawBoundary(borderColor)
        var lastPosition: FramePosition = ansiGridStringBuilder.drawWrapped(title, leftMargin, 2, availableWidth, titleColor)
        ansiGridStringBuilder.drawUnderline(leftMargin, lastPosition.y + 1, title.length, titleColor)

        if (description.isNotEmpty()) {
            lastPosition = ansiGridStringBuilder.drawCentralisedWrapped(description.ensureFinishedSentence(), lastPosition.y + 3, availableWidth, descriptionColor)
        }

        lastPosition = FramePosition(lastPosition.x, lastPosition.y + 2)

        commands.forEach {
            if (lastPosition.y < game.frameSize.height - 1) {
                if (it.command.isNotEmpty() && it.description.isNotEmpty()) {
                    lastPosition = ansiGridStringBuilder.drawWrapped(it.command, leftMargin, lastPosition.y + 1, availableWidth, commandColor)
                    lastPosition = ansiGridStringBuilder.drawWrapped("-", leftMargin + padding, lastPosition.y, availableWidth, commandColor)
                    lastPosition = ansiGridStringBuilder.drawWrapped(it.description, leftMargin + padding + 2, lastPosition.y, availableWidth, descriptionColor)
                } else if (it.command.isNotEmpty() && it.description.isEmpty()) {
                    lastPosition = ansiGridStringBuilder.drawWrapped(it.command, leftMargin, lastPosition.y + 1, availableWidth, commandDescriptionColor)
                }
            }
        }

        return AnsiGridTextFrame(ansiGridStringBuilder, 0, 0, backgroundColor).also {
            it.acceptsInput = false
        }
    }
}
