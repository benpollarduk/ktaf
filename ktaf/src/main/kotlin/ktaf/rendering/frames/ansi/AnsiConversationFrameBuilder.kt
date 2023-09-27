package ktaf.rendering.frames.ansi

import ktaf.assets.Size
import ktaf.conversations.Converser
import ktaf.conversations.LogItem
import ktaf.conversations.Participant
import ktaf.interpretation.CommandHelp
import ktaf.rendering.FramePosition
import ktaf.rendering.frames.ConversationFrameBuilder
import ktaf.rendering.frames.Frame

/**
 * Provides an ANSI conversation frame builder that builds in to the specified [ansiGridStringBuilder].
 */
public class AnsiConversationFrameBuilder(
    private val ansiGridStringBuilder: AnsiGridStringBuilder,
    private val frameSize: Size,
    private val backgroundColor: AnsiColor = AnsiColor.RESET,
    private val borderColor: AnsiColor = AnsiColor.BRIGHT_BLACK,
    private val titleColor: AnsiColor = AnsiColor.GREEN,
    private val nonPlayerMessageColor: AnsiColor = AnsiColor.YELLOW,
    private val playerMessageColor: AnsiColor = AnsiColor.BLUE,
    private val responseColor: AnsiColor = AnsiColor.BRIGHT_BLACK,
    private val inputColor: AnsiColor = AnsiColor.WHITE
) : ConversationFrameBuilder {
    private fun truncateLog(startX: Int, availableWidth: Int, availableHeight: Int, log: List<LogItem>): List<LogItem> {
        val truncated = mutableListOf<LogItem>()
        var remainingHeight = availableHeight

        for (i in log.size - 1 downTo 0) {
            val lines = ansiGridStringBuilder.getNumberOfLines(log[i].line, startX, availableWidth)
            remainingHeight -= lines

            if (remainingHeight >= 0) {
                truncated.add(log[i])
            } else {
                break
            }
        }

        truncated.reverse()
        return truncated.toList()
    }

    override fun build(title: String, converser: Converser, commands: List<CommandHelp>): Frame {
        val availableWidth = frameSize.width - 4
        val availableHeight = frameSize.height - 2
        val leftMargin = 2
        val linePadding = 2
        var lastPosition = FramePosition(0, 2)
        val log = converser.conversation.log

        ansiGridStringBuilder.resize(frameSize)
        ansiGridStringBuilder.drawBoundary(borderColor)

        if (title.isNotEmpty()) {
            lastPosition = ansiGridStringBuilder.drawWrapped(
                title,
                leftMargin,
                lastPosition.y,
                availableWidth,
                titleColor
            )
            ansiGridStringBuilder.drawUnderline(
                leftMargin,
                lastPosition.y + 1,
                title.length,
                titleColor
            )
            lastPosition = FramePosition(lastPosition.x, lastPosition.y + 2)
        }

        if (log.any()) {
            val spaceForLog = availableHeight - 10 - commands.size
            val truncatedLog = truncateLog(leftMargin, availableWidth, spaceForLog, log)
            truncatedLog.forEach {
                lastPosition = FramePosition(lastPosition.x, lastPosition.y + 1)
                val converserName = converser.identifier.name

                lastPosition = when (it.participant) {
                    Participant.PLAYER -> {
                        ansiGridStringBuilder.drawWrapped(
                            "You: ${it.line}",
                            leftMargin,
                            lastPosition.y,
                            availableWidth,
                            playerMessageColor
                        )
                    }
                    Participant.OTHER -> {
                        ansiGridStringBuilder.drawWrapped(
                            "$converserName: ${it.line}",
                            leftMargin,
                            lastPosition.y,
                            availableWidth,
                            nonPlayerMessageColor
                        )
                    }
                }
            }
        }

        if (commands.any()) {
            ansiGridStringBuilder.drawHorizontalDivider(lastPosition.y + linePadding, borderColor)
            lastPosition = ansiGridStringBuilder.drawWrapped(
                "You can: ",
                leftMargin,
                lastPosition.y + 4,
                availableWidth,
                responseColor
            )
            val maxCommandLength = commands.maxOf { it.command.length }
            val padding = 4
            val dashStartX = leftMargin + maxCommandLength + padding
            val descriptionStartX = dashStartX + 2
            lastPosition = FramePosition(lastPosition.x, lastPosition.y + 1)

            commands.forEach {
                lastPosition = ansiGridStringBuilder.drawWrapped(
                    it.command,
                    leftMargin,
                    lastPosition.y + 1,
                    availableWidth,
                    responseColor
                )
                lastPosition = ansiGridStringBuilder.drawWrapped(
                    "-",
                    dashStartX,
                    lastPosition.y,
                    availableWidth,
                    responseColor
                )
                lastPosition = ansiGridStringBuilder.drawWrapped(
                    it.description,
                    descriptionStartX,
                    lastPosition.y,
                    availableWidth,
                    responseColor
                )
            }
        }

        ansiGridStringBuilder.drawHorizontalDivider(availableHeight - 1, borderColor)
        ansiGridStringBuilder.drawWrapped(">", leftMargin, availableHeight, availableWidth, inputColor)

        return AnsiGridTextFrame(
            ansiGridStringBuilder,
            5,
            frameSize.height - 1,
            backgroundColor
        ).also {
            it.acceptsInput = true
        }
    }
}
