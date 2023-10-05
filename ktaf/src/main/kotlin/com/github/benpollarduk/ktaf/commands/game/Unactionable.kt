package com.github.benpollarduk.ktaf.commands.game

import com.github.benpollarduk.ktaf.assets.interaction.Reaction
import com.github.benpollarduk.ktaf.assets.interaction.ReactionResult
import com.github.benpollarduk.ktaf.commands.Command
import com.github.benpollarduk.ktaf.logic.Game

/**
 * Provides an unactionable command.
 */
internal class Unactionable(private val description: String) : Command {
    override fun invoke(game: Game): Reaction {
        return Reaction(ReactionResult.ERROR, description)
    }
}
