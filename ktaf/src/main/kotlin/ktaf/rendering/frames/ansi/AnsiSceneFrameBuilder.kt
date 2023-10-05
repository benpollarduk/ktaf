package ktaf.rendering.frames.ansi

import ktaf.assets.Size
import ktaf.assets.characters.PlayableCharacter
import ktaf.assets.locations.Region
import ktaf.assets.locations.Room
import ktaf.assets.locations.ViewPoint
import ktaf.commands.game.Move
import ktaf.extensions.addSentence
import ktaf.extensions.ensureFinishedSentence
import ktaf.extensions.insensitiveEquals
import ktaf.interpretation.CommandHelp
import ktaf.rendering.FramePosition
import ktaf.rendering.KeyType
import ktaf.rendering.frames.Frame
import ktaf.rendering.frames.SceneFrameBuilder
import ktaf.utilities.StringUtilities
import kotlin.math.max

/**
 * Provides an ANSI scene frame builder that builds in to the specified [ansiGridStringBuilder].
 */
@Suppress("LongParameterList", "LongMethod")
public class AnsiSceneFrameBuilder(
    private val ansiGridStringBuilder: AnsiGridStringBuilder,
    private val roomMapBuilder: AnsiRoomMapBuilder,
    private val frameSize: Size,
    private val backgroundColor: AnsiColor = AnsiColor.RESET,
    private val borderColor: AnsiColor = AnsiColor.BRIGHT_BLACK,
    private val textColor: AnsiColor = AnsiColor.WHITE,
    private val inputColor: AnsiColor = AnsiColor.WHITE,
    private val commandsColor: AnsiColor = AnsiColor.WHITE,
    private val displayMessagesInIsolation: Boolean = true,
    private val suppressMovementMessages: Boolean = true
) : SceneFrameBuilder {
    private fun isMovementConfirmation(message: String): Boolean {
        if (message.isNotEmpty()) {
            for (direction in Region.allDirections) {
                if (message.insensitiveEquals("${Move.SUCCESSFUL_MOVE_PREFIX} $direction.")) {
                    return true
                }
            }
        }

        return false
    }

    override fun build(
        room: Room,
        viewPoint: ViewPoint,
        playableCharacter: PlayableCharacter,
        message: String,
        contextualCommands: List<CommandHelp>,
        keyType: KeyType
    ): Frame {
        val availableWidth = frameSize.width - 4
        val availableHeight = frameSize.height - 2
        val leftMargin = 2
        val linePadding = 2
        val displayMessage = message.isNotEmpty() && (!isMovementConfirmation(message) || !suppressMovementMessages)
        val acceptInput = !(displayMessagesInIsolation && displayMessage)

        ansiGridStringBuilder.resize(frameSize)
        ansiGridStringBuilder.drawBoundary(borderColor)
        var lastPosition: FramePosition = ansiGridStringBuilder.drawWrapped(
            room.identifier.name,
            leftMargin,
            2,
            availableWidth,
            textColor
        )
        ansiGridStringBuilder.drawUnderline(leftMargin, lastPosition.y + 1, room.identifier.name.length, textColor)

        if (displayMessagesInIsolation && displayMessage) {
            ansiGridStringBuilder.drawWrapped(
                message.ensureFinishedSentence(),
                leftMargin,
                lastPosition.y + 3,
                availableWidth,
                textColor
            )
        } else {
            lastPosition = ansiGridStringBuilder.drawWrapped(
                room.description.getDescription().ensureFinishedSentence(),
                leftMargin,
                lastPosition.y + 3,
                availableWidth,
                textColor
            )

            var extendedDescription: String = if (room.items.any()) {
                room.examine().description.ensureFinishedSentence()
            } else {
                "There are no items in this area."
            }

            extendedDescription = extendedDescription.addSentence(StringUtilities.createNPCString(room))

            if (viewPoint.any) {
                extendedDescription = extendedDescription.addSentence(
                    StringUtilities.createViewpointAsString(room, viewPoint)
                )
            }

            lastPosition = ansiGridStringBuilder.drawWrapped(
                extendedDescription,
                leftMargin,
                lastPosition.y + linePadding,
                availableWidth,
                textColor
            )
            lastPosition = roomMapBuilder.build(
                ansiGridStringBuilder,
                room,
                viewPoint,
                keyType,
                leftMargin,
                lastPosition.y + linePadding
            )

            if (playableCharacter.items.any()) {
                lastPosition = ansiGridStringBuilder.drawWrapped(
                    "You have: " + StringUtilities.constructExaminablesAsSentence(playableCharacter.items),
                    leftMargin,
                    lastPosition.y + 2,
                    availableWidth,
                    textColor
                )
            }

            if (!displayMessagesInIsolation && !displayMessage) {
                ansiGridStringBuilder.drawHorizontalDivider(lastPosition.y + 3, borderColor)
                lastPosition = ansiGridStringBuilder.drawWrapped(
                    message.ensureFinishedSentence(),
                    leftMargin,
                    lastPosition.y + 5,
                    availableWidth,
                    textColor
                )
            }

            if (contextualCommands.any()) {
                val requiredSpaceForDivider = 3
                val requiredSpaceForPrompt = 4
                val requiredSpaceForCommandHeader = 3
                val requiredYToFitAllCommands = frameSize.height - requiredSpaceForCommandHeader -
                    requiredSpaceForPrompt - requiredSpaceForDivider - contextualCommands.size
                val yStart = max(requiredYToFitAllCommands, lastPosition.y)
                val maxCommandLength = contextualCommands.maxOf { it.command.length }
                val padding = 4
                val dashStartX = leftMargin + maxCommandLength + padding
                val descptiptionStartX = dashStartX + 2
                lastPosition = FramePosition(lastPosition.x, yStart)

                ansiGridStringBuilder.drawHorizontalDivider(lastPosition.y + linePadding, borderColor)
                lastPosition = ansiGridStringBuilder.drawWrapped(
                    "You can:",
                    leftMargin,
                    lastPosition.y + 4,
                    availableWidth,
                    commandsColor
                )
                lastPosition = FramePosition(lastPosition.x, lastPosition.y + 1)

                contextualCommands.forEach {
                    lastPosition = ansiGridStringBuilder.drawWrapped(
                        it.command,
                        leftMargin,
                        lastPosition.y + 1,
                        availableWidth,
                        commandsColor
                    )
                    lastPosition = ansiGridStringBuilder.drawWrapped(
                        "-",
                        dashStartX,
                        lastPosition.y,
                        availableWidth,
                        commandsColor
                    )
                    ansiGridStringBuilder.drawWrapped(
                        it.description,
                        descptiptionStartX,
                        lastPosition.y,
                        availableWidth,
                        commandsColor
                    )
                }
            }

            ansiGridStringBuilder.drawHorizontalDivider(availableHeight - 1, borderColor)
            ansiGridStringBuilder.drawWrapped(">", leftMargin, availableHeight, availableWidth, inputColor)
        }

        return AnsiGridTextFrame(
            ansiGridStringBuilder,
            5,
            frameSize.height - 1,
            acceptInput,
            backgroundColor
        )
    }
}
