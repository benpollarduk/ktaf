package com.github.benpollarduk.ktaf.commands.global

import com.github.benpollarduk.ktaf.assets.interaction.Reaction
import com.github.benpollarduk.ktaf.assets.interaction.ReactionResult
import com.github.benpollarduk.ktaf.commands.Command
import com.github.benpollarduk.ktaf.logic.Game

/**
 * Provides a command to display the map frame.
 */
internal class Map : Command {
    override fun invoke(game: Game): Reaction {
        game.displayMap()
        return Reaction(ReactionResult.INTERNAL, "")
    }
}
