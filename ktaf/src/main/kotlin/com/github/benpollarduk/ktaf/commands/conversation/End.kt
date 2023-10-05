package com.github.benpollarduk.ktaf.commands.conversation

import com.github.benpollarduk.ktaf.assets.interaction.Reaction
import com.github.benpollarduk.ktaf.assets.interaction.ReactionResult
import com.github.benpollarduk.ktaf.commands.Command
import com.github.benpollarduk.ktaf.logic.Game

/**
 * Provides a command to end a [Conversation].
 */
internal class End : Command {
    override fun invoke(game: Game): Reaction {
        game.endConversation()
        return Reaction(ReactionResult.OK, "Ended the conversation.")
    }
}
