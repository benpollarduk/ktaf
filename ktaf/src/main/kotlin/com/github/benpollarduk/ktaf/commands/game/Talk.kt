package com.github.benpollarduk.ktaf.commands.game

import com.github.benpollarduk.ktaf.assets.characters.Character
import com.github.benpollarduk.ktaf.assets.interaction.Reaction
import com.github.benpollarduk.ktaf.assets.interaction.ReactionResult
import com.github.benpollarduk.ktaf.commands.Command
import com.github.benpollarduk.ktaf.conversations.Converser
import com.github.benpollarduk.ktaf.logic.Game

/**
 * Provides a command to talk to a specified [converser].
 */
internal class Talk(private val converser: Converser?) : Command {
    override fun invoke(game: Game): Reaction {
        if (converser == null) {
            return Reaction(ReactionResult.ERROR, "Can't converse.")
        }

        if (converser is Character && !converser.isAlive) {
            return Reaction(ReactionResult.ERROR, "${converser.identifier} is dead.")
        }

        game.startConversation(converser)
        return Reaction(ReactionResult.INTERNAL, "Engaged in conversation.")
    }
}
