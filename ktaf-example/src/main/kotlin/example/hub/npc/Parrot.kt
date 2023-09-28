package example.hub.npc

import ktaf.assets.characters.NonPlayableCharacter
import ktaf.assets.characters.PlayableCharacter
import ktaf.assets.locations.Room
import ktaf.conversations.Conversation
import ktaf.conversations.Paragraph
import ktaf.conversations.Response
import ktaf.utilities.templates.NonPlayableCharacterTemplate

internal class Parrot : NonPlayableCharacterTemplate() {
    override fun instantiate(playableCharacter: PlayableCharacter, room: Room): NonPlayableCharacter {
        return NonPlayableCharacter(
            NAME,
            DESCRIPTION
        ).also {
            it.conversation = Conversation(
                listOf(
                    Paragraph("Squarrrkkk"),
                    Paragraph("Will you feed me?").also {
                        it.responses = listOf(
                            Response("I don't have any food to feed you with!", 1),
                            Response("Sure here's some food!", 2),
                            Response("I'll try bring you some.", 3)
                        )
                    },
                    Paragraph("Bring some next time then!", -1),
                    Paragraph("You don't have any food! Errkggg!", -2),
                    Paragraph("Thanks a lot! Erckgah!", -3)
                )
            )
        }
    }

    internal companion object {
        internal const val NAME = "Parrot"
        private const val DESCRIPTION = "A brightly colored parrot."
    }
}
