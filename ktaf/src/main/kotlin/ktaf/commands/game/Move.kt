package ktaf.commands.game

import ktaf.assets.interaction.Reaction
import ktaf.assets.interaction.ReactionResult
import ktaf.assets.locations.Direction
import ktaf.commands.Command
import ktaf.logic.Game

/**
 * Provides a command to move in a specified [direction].
 */
internal class Move(private val direction: Direction) : Command {
    override fun invoke(game: Game): Reaction {
        if (game.overworld.currentRegion?.move(direction) == true) {
            return Reaction(ReactionResult.OK, "$successfulMovePrefix $direction.")
        }
        return Reaction(ReactionResult.ERROR, "Could not move $direction.")
    }

    internal companion object {
        /**
         * A prefix for successful moves.
         */
        internal const val successfulMovePrefix: String = "Moved"
    }
}
