package ktaf.commands.game

import ktaf.assets.Examinable
import ktaf.assets.interaction.Reaction
import ktaf.assets.interaction.ReactionResult
import ktaf.commands.Command
import ktaf.logic.Game

/**
 * Provides a command to examine a specified [examinable].
 */
internal class Examine(private val examinable: Examinable?) : Command {
    override fun invoke(game: Game): Reaction {
        if (examinable == null) {
            return Reaction(ReactionResult.ERROR, "You must specify an examinable.")
        }

        return Reaction(ReactionResult.OK, examinable.examine().description)
    }
}
