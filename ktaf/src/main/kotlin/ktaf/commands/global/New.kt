package ktaf.commands.global

import ktaf.assets.interaction.Reaction
import ktaf.assets.interaction.ReactionResult
import ktaf.commands.Command
import ktaf.logic.Game

/**
 * Provides a command to start a new [Game].
 */
internal class New : Command {
    override fun invoke(game: Game): Reaction {
        game.end()
        return Reaction(ReactionResult.OK, "New game.")
    }
}
