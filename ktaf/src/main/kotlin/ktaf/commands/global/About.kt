package ktaf.commands.global

import ktaf.assets.interaction.Reaction
import ktaf.assets.interaction.ReactionResult
import ktaf.commands.Command
import ktaf.logic.Game

/**
 * Provides a command to display the about frame.
 */
internal class About : Command {
    override fun invoke(game: Game): Reaction {
        game.displayAbout()
        return Reaction(ReactionResult.INTERNAL, "")
    }
}
