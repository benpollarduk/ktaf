package ktaf.commands.global

import ktaf.assets.interaction.Reaction
import ktaf.assets.interaction.ReactionResult
import ktaf.commands.Command
import ktaf.logic.Game

/**
 * Provides a command to display the map frame.
 */
internal class Map : Command {
    override fun invoke(game: Game): Reaction {
        game.displayMap()
        return Reaction(ReactionResult.INTERNAL, "")
    }
}
