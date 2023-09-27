package example.hub.npc

import ktaf.assets.characters.NonPlayableCharacter
import ktaf.assets.characters.PlayableCharacter
import ktaf.assets.locations.Room
import ktaf.conversations.Conversation
import ktaf.conversations.Paragraph
import ktaf.utilities.templates.NonPlayableCharacterTemplate

internal class Parrot : NonPlayableCharacterTemplate() {
    override fun instantiate(playableCharacter: PlayableCharacter, room: Room): NonPlayableCharacter {
        return NonPlayableCharacter(
            NAME,
            DESCRIPTION,
        ).also {
            it.conversation = Conversation(
                listOf(
                    Paragraph("Squarrrkkk"),
                    Paragraph("Mode changing not implemented yet, come back soon!"),
                ),
            )
        }
    }

    internal companion object {
        internal const val NAME = "Parrot"
        private const val DESCRIPTION = "A brightly colored parrot."
    }
}
