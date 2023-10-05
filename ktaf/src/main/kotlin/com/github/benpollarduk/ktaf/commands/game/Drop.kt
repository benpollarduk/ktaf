package com.github.benpollarduk.ktaf.commands.game

import com.github.benpollarduk.ktaf.assets.Item
import com.github.benpollarduk.ktaf.assets.interaction.Reaction
import com.github.benpollarduk.ktaf.assets.interaction.ReactionResult
import com.github.benpollarduk.ktaf.commands.Command
import com.github.benpollarduk.ktaf.logic.Game

/**
 * Provides a command to drop a specified [item].
 */
internal class Drop(private val item: Item?) : Command {
    override fun invoke(game: Game): Reaction {
        if (item == null) {
            return Reaction(ReactionResult.ERROR, "You must specify an item.")
        }

        if (!game.player.hasItem(item)) {
            return Reaction(ReactionResult.ERROR, "You don't have that ${item.identifier}.")
        }

        game.overworld.currentRegion?.currentRoom?.addItem(item)
        game.player.decquireItem(item)
        return Reaction(ReactionResult.OK, "Dropped ${item.identifier}.")
    }
}
