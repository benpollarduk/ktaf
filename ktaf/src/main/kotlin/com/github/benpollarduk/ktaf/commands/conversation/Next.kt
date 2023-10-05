package com.github.benpollarduk.ktaf.commands.conversation

import com.github.benpollarduk.ktaf.assets.interaction.Reaction
import com.github.benpollarduk.ktaf.assets.interaction.ReactionResult
import com.github.benpollarduk.ktaf.commands.Command
import com.github.benpollarduk.ktaf.logic.Game

/**
 * Provides a command to continue the [Conversation].
 */
internal class Next : Command {
    override fun invoke(game: Game): Reaction {
        val converser = game.activeConverser ?: return Reaction(ReactionResult.ERROR, "No converser.")
        return converser.conversation.next(game)
    }
}
