package ktaf.commands.global

import ktaf.assets.interaction.Reaction
import ktaf.assets.interaction.ReactionResult
import ktaf.commands.Command
import ktaf.logic.Game

/**
 * Provides a command to display the help frame.
 */
internal class Help : Command {
    override fun invoke(game: Game): Reaction {
        game.displayHelp()
        return Reaction(ReactionResult.INTERNAL, "")
    }
}
