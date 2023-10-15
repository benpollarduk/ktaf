package com.github.benpollarduk.ktaf.commands.global

import com.github.benpollarduk.ktaf.assets.interaction.Reaction
import com.github.benpollarduk.ktaf.assets.interaction.ReactionResult
import com.github.benpollarduk.ktaf.commands.Command
import com.github.benpollarduk.ktaf.logic.Game
import com.github.benpollarduk.ktaf.logic.GameExecutor

/**
 * Provides a command to start a new [Game].
 */
internal class New : Command {
    override fun invoke(game: Game): Reaction {
        GameExecutor.cancel(game)
        return Reaction(ReactionResult.OK, "New game.")
    }
}
