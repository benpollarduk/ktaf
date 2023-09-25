package ktaf.commands.conversation

import ktaf.assets.interaction.Reaction
import ktaf.assets.interaction.ReactionResult
import ktaf.commands.Command
import ktaf.logic.Game

/**
 * Provides a command to end a [Conversation].
 */
internal class End : Command {
    override fun invoke(game: Game): Reaction {
        game.endConversation()
        return Reaction(ReactionResult.OK, "Ended the conversation.")
    }
}
