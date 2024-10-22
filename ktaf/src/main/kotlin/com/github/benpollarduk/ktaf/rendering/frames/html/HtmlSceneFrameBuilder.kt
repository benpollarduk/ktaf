package com.github.benpollarduk.ktaf.rendering.frames.html

import com.github.benpollarduk.ktaf.assets.ExaminationScene
import com.github.benpollarduk.ktaf.assets.Item
import com.github.benpollarduk.ktaf.assets.Size
import com.github.benpollarduk.ktaf.assets.attributes.Attribute
import com.github.benpollarduk.ktaf.assets.characters.PlayableCharacter
import com.github.benpollarduk.ktaf.assets.locations.Region
import com.github.benpollarduk.ktaf.assets.locations.Room
import com.github.benpollarduk.ktaf.assets.locations.ViewPoint
import com.github.benpollarduk.ktaf.commands.game.Move
import com.github.benpollarduk.ktaf.extensions.addSentence
import com.github.benpollarduk.ktaf.extensions.ensureFinishedSentence
import com.github.benpollarduk.ktaf.extensions.insensitiveEquals
import com.github.benpollarduk.ktaf.extensions.removeWhitespaceLines
import com.github.benpollarduk.ktaf.interpretation.CommandHelp
import com.github.benpollarduk.ktaf.rendering.KeyType
import com.github.benpollarduk.ktaf.rendering.frames.Frame
import com.github.benpollarduk.ktaf.rendering.frames.GridRoomMapBuilder
import com.github.benpollarduk.ktaf.rendering.frames.GridStringBuilder
import com.github.benpollarduk.ktaf.rendering.frames.SceneFrameBuilder
import com.github.benpollarduk.ktaf.utilities.NEWLINE
import com.github.benpollarduk.ktaf.utilities.StringUtilities

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

    private fun addPlayerItems(items: List<Item>) {
        if (items.any()) {
            htmlPageBuilder.p("You have: " + StringUtilities.constructExaminablesAsSentence(items))
        }
    }

    private fun addPlayerAttributes(attributes: Map<Attribute, Int>) {
        if (attributes.any()) {
            htmlPageBuilder.p(StringUtilities.getAttributesAsString(attributes))
        }
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
                room.examine(ExaminationScene(playableCharacter, room)).description.ensureFinishedSentence()
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
            htmlPageBuilder.pre(map.replace(NEWLINE, "<br>"))

            addPlayerItems(playableCharacter.items)
            addPlayerAttributes(playableCharacter.attributes.toMap())

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
