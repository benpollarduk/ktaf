package com.github.benpollarduk.ktaf.commands.game

import com.github.benpollarduk.ktaf.assets.Item
import com.github.benpollarduk.ktaf.assets.interaction.Reaction
import com.github.benpollarduk.ktaf.assets.interaction.ReactionResult
import com.github.benpollarduk.ktaf.assets.locations.Room
import com.github.benpollarduk.ktaf.commands.Command
import com.github.benpollarduk.ktaf.logic.Game

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
