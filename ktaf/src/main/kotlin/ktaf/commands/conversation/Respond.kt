package ktaf.commands.conversation

import ktaf.assets.interaction.Reaction
import ktaf.assets.interaction.ReactionResult
import ktaf.commands.Command
import ktaf.conversations.Response
import ktaf.logic.Game

/**
 * Provides a command to respond to a [Conversation] with a [response].
 */
internal class Respond(private val response: Response) : Command {
    override fun invoke(game: Game): Reaction {
        val converser = game.activeConverser ?: return Reaction(ReactionResult.ERROR, "No converser.")
        return converser.conversation.respond(response, game)
    }
}
