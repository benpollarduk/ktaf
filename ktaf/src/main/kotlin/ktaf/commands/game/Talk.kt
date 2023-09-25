package ktaf.commands.game

import ktaf.assets.characters.Character
import ktaf.assets.interaction.Reaction
import ktaf.assets.interaction.ReactionResult
import ktaf.commands.Command
import ktaf.conversations.Converser
import ktaf.logic.Game

/**
 * Provides a command to talk to a specified [converser].
 */
internal class Talk(private val converser: Converser?) : Command {
    override fun invoke(game: Game): Reaction {
        if (converser == null) {
            return Reaction(ReactionResult.ERROR, "Can't converse.")
        }

        if (converser is Character && !converser.isAlive) {
            return Reaction(ReactionResult.ERROR, "${converser.identifier} is dead.")
        }

        game.startConversation(converser)
        return Reaction(ReactionResult.INTERNAL, "Engaged in conversation.")
    }
}
