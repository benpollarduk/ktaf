package ktaf.commands.frame

import ktaf.assets.interaction.Reaction
import ktaf.assets.interaction.ReactionResult
import ktaf.commands.Command
import ktaf.logic.Game

/**
 * Provides a command to turn off displaying the commands list.
 */
internal class CommandsOff : Command {
    override fun invoke(game: Game): Reaction {
        game.displayCommandListInSceneFrames = false
        return Reaction(ReactionResult.OK, "Commands have been turned off.")
    }
}
