package com.github.benpollarduk.ktaf.example.hub.npc

import com.github.benpollarduk.ktaf.assets.characters.NonPlayableCharacter
import com.github.benpollarduk.ktaf.conversations.Conversation
import com.github.benpollarduk.ktaf.conversations.Paragraph
import com.github.benpollarduk.ktaf.conversations.Response
import com.github.benpollarduk.ktaf.utilities.templates.AssetTemplate

internal class Parrot : AssetTemplate<NonPlayableCharacter> {
    override fun instantiate(): NonPlayableCharacter {
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
