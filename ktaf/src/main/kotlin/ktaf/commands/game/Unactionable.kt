package ktaf.commands.game

import ktaf.assets.interaction.Reaction
import ktaf.assets.interaction.ReactionResult
import ktaf.commands.Command
import ktaf.logic.Game

/**
 * Provides an unactionable command.
 */
internal class Unactionable(private val description: String) : Command {
    override fun invoke(game: Game): Reaction {
        return Reaction(ReactionResult.ERROR, description)
    }
}
