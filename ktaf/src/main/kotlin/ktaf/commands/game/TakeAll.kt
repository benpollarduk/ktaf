package ktaf.commands.game

import ktaf.assets.Item
import ktaf.assets.interaction.Reaction
import ktaf.assets.interaction.ReactionResult
import ktaf.assets.locations.Room
import ktaf.commands.Command
import ktaf.logic.Game

/**
 * Provides a command to take all [Item] in a [Room].
 */
internal class TakeAll : Command {
    override fun invoke(game: Game): Reaction {
        val room = game.overworld.currentRegion?.currentRoom ?: return Reaction(ReactionResult.ERROR, "Not in a room.")
        var itemsAsString = ""

        room.items.filter { it.isTakeable }.forEach {
            room.removeItem(it)
            game.player.acquireItem(it)
            itemsAsString += "${it.identifier}, "
        }

        if (itemsAsString.isNotEmpty()) {
            itemsAsString = itemsAsString.removeSuffix(", ")
            itemsAsString = "Took $itemsAsString"
            return Reaction(ReactionResult.OK, itemsAsString)
        }

        return Reaction(ReactionResult.ERROR, "Nothing to take.")
    }
}
