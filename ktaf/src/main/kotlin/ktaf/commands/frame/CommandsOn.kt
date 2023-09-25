package ktaf.commands.frame

import ktaf.assets.interaction.Reaction
import ktaf.assets.interaction.ReactionResult
import ktaf.commands.Command
import ktaf.logic.Game

/**
 * Provides a command to turn on displaying the commands list.
 */
internal class CommandsOn : Command {
    override fun invoke(game: Game): Reaction {
        game.displayCommandListInSceneFrames = true
        return Reaction(ReactionResult.OK, "Commands have been turned on.")
    }
}
