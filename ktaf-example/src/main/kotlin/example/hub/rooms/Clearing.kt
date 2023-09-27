package example.hub.rooms

import ktaf.assets.characters.NonPlayableCharacter
import ktaf.assets.characters.PlayableCharacter
import ktaf.assets.locations.Room
import ktaf.conversations.Conversation
import ktaf.conversations.Paragraph
import ktaf.utilities.templates.RoomTemplate

internal class Clearing : RoomTemplate() {
    override fun instantiate(playableCharacter: PlayableCharacter): Room {
        val npc = NonPlayableCharacter(
            "Parrot",
            "A brightly colored parrot."
        ).also {
            it.conversation = Conversation(
                listOf(
                    Paragraph("Squarrrkkk"),
                    Paragraph("Mode changing not implemented yet, come back soon!")
                )
            )
        }
        val room = Room(NAME, DESCRIPTION).also {
            it.addCharacter(npc)
        }

        return room
    }

    internal companion object {
        internal const val NAME = "Jungle Clearing"
        private const val DESCRIPTION = "You are in a small clearing in a jungle, tightly enclosed by undergrowth. " +
            "You have no idea how you got here. The chirps and buzzes coming from insects in the undergrowth are intense. There are some stone pedestals in front of you. Each has a small globe on top of it."
    }
}
