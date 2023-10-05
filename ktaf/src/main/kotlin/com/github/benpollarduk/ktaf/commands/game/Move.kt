package com.github.benpollarduk.ktaf.commands.game

import com.github.benpollarduk.ktaf.assets.interaction.Reaction
import com.github.benpollarduk.ktaf.assets.interaction.ReactionResult
import com.github.benpollarduk.ktaf.assets.locations.Direction
import com.github.benpollarduk.ktaf.commands.Command
import com.github.benpollarduk.ktaf.logic.Game

/**
 * Provides a command to move in a specified [direction].
 */
internal class Move(private val direction: Direction) : Command {
    override fun invoke(game: Game): Reaction {
        if (game.overworld.currentRegion?.move(direction) == true) {
            return Reaction(ReactionResult.OK, "$SUCCESSFUL_MOVE_PREFIX $direction.")
        }
        return Reaction(ReactionResult.ERROR, "Could not move $direction.")
    }

    internal companion object {
        /**
         * A prefix for successful moves.
         */
        internal const val SUCCESSFUL_MOVE_PREFIX: String = "Moved"
    }
}
