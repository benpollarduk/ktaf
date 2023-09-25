package ktaf.commands.global

import ktaf.assets.interaction.Reaction
import ktaf.assets.interaction.ReactionResult
import ktaf.commands.Command
import ktaf.logic.Game

/**
 * Provides a command to end the [game].
 */
internal class Exit : Command {
    override fun invoke(game: Game): Reaction {
        game.end()
        return Reaction(ReactionResult.OK, "Exiting...")
    }
}
