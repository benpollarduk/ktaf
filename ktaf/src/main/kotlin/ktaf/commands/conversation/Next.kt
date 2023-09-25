package ktaf.commands.conversation

import ktaf.assets.interaction.Reaction
import ktaf.assets.interaction.ReactionResult
import ktaf.commands.Command
import ktaf.logic.Game

/**
 * Provides a command to continue the [Conversation].
 */
internal class Next : Command {
    override fun invoke(game: Game): Reaction {
        val converser = game.activeConverser ?: return Reaction(ReactionResult.ERROR, "No converser.")
        return converser.conversation.next(game)
    }
}
