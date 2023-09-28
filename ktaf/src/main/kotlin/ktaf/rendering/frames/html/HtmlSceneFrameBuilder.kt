package ktaf.rendering.frames.html

import ktaf.assets.Size
import ktaf.assets.characters.PlayableCharacter
import ktaf.assets.locations.Region
import ktaf.assets.locations.Room
import ktaf.assets.locations.ViewPoint
import ktaf.commands.game.Move
import ktaf.extensions.addSentence
import ktaf.extensions.ensureFinishedSentence
import ktaf.extensions.insensitiveEquals
import ktaf.extensions.removeWhitespaceLines
import ktaf.helpers.newline
import ktaf.interpretation.CommandHelp
import ktaf.rendering.KeyType
import ktaf.rendering.frames.Frame
import ktaf.rendering.frames.GridRoomMapBuilder
import ktaf.rendering.frames.GridStringBuilder
import ktaf.rendering.frames.SceneFrameBuilder
import ktaf.utilities.StringUtilities

/**
 * Provides an HTML scene frame builder.
 */
public class HtmlSceneFrameBuilder(
    private val htmlPageBuilder: HtmlPageBuilder,
    private val roomMapBuilder: GridRoomMapBuilder,
    private val frameSize: Size,
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
        val leftMargin = 2
        val displayMessage = message.isNotEmpty() && (!isMovementConfirmation(message) || !suppressMovementMessages)
        val acceptInput = !(displayMessagesInIsolation && displayMessage)

        htmlPageBuilder.h1(room.identifier.name)
        htmlPageBuilder.br()

        if (displayMessagesInIsolation && displayMessage) {
            htmlPageBuilder.p(message.ensureFinishedSentence())
        } else {
            htmlPageBuilder.p(room.description.getDescription().ensureFinishedSentence())

            var extendedDescription: String = if (room.items.any()) {
                room.examine().description.ensureFinishedSentence()
            } else {
                "There are no items in this area."
            }

            extendedDescription = extendedDescription.addSentence(StringUtilities.createNPCString(room))

            if (viewPoint.any) {
                extendedDescription = extendedDescription.addSentence(
                    StringUtilities.createViewpointAsString(
                        room,
                        viewPoint
                    )
                )
            }

            htmlPageBuilder.p(extendedDescription)

            val gridStringBuilder = GridStringBuilder().also {
                it.resize(frameSize)
            }

            roomMapBuilder.build(gridStringBuilder, room, viewPoint, keyType, leftMargin, 0)
            var map = gridStringBuilder.toString().removeWhitespaceLines()
            htmlPageBuilder.pre(map.replace(newline(), "<br>"))

            if (playableCharacter.items.any()) {
                htmlPageBuilder.p(
                    "You have: " + StringUtilities.constructExaminablesAsSentence(
                        playableCharacter.items
                    )
                )
            }

            if (!displayMessagesInIsolation && !displayMessage) {
                htmlPageBuilder.p(message.ensureFinishedSentence())
            }

            if (contextualCommands.any()) {
                htmlPageBuilder.p("You can:")

                contextualCommands.forEach {
                    htmlPageBuilder.br()
                    htmlPageBuilder.b(it.command)
                    htmlPageBuilder.append(" - ${it.description}")
                    htmlPageBuilder.br()
                }
            }
        }

        return HtmlFrame(htmlPageBuilder, acceptInput)
    }
}
