package ktaf.commands.game

import ktaf.assets.Item
import ktaf.assets.interaction.Reaction
import ktaf.assets.interaction.ReactionResult
import ktaf.commands.Command
import ktaf.logic.Game

/**
 * Provides a command to take a specified [item].
 */
internal class Take(private val item: Item?) : Command {
    override fun invoke(game: Game): Reaction {
        if (item == null) {
            return Reaction(ReactionResult.ERROR, "You must specify an item.")
        }

        val room = game.overworld.currentRegion?.currentRoom ?: return Reaction(ReactionResult.ERROR, "No room.")

        if (!room.containsItem(item)) {
            return Reaction(ReactionResult.ERROR, "${room.identifier} does not contain ${item.identifier}.")
        }

        if (!item.isTakeable) {
            return Reaction(ReactionResult.ERROR, "${item.identifier} is not takeable.")
        }

        room.removeItem(item)
        game.player.acquireItem(item)
        return Reaction(ReactionResult.OK, "Took ${item.identifier}.")
    }
}
