package com.github.benpollarduk.ktaf.example.everglades.rooms

import com.github.benpollarduk.ktaf.assets.interaction.Reaction
import com.github.benpollarduk.ktaf.assets.interaction.ReactionResult
import com.github.benpollarduk.ktaf.assets.locations.Direction
import com.github.benpollarduk.ktaf.assets.locations.Exit
import com.github.benpollarduk.ktaf.assets.locations.Room
import com.github.benpollarduk.ktaf.commands.CustomCommand
import com.github.benpollarduk.ktaf.interpretation.CommandHelp
import com.github.benpollarduk.ktaf.utilities.templates.AssetTemplate

internal class TreeHouse : AssetTemplate<Room> {
    override fun instantiate(): Room {
        return Room(
            NAME,
            DESCRIPTION,
            listOf(
                Exit(Direction.DOWN)
            )
        ).also {
            it.commands = listOf(
                CustomCommand(
                    CommandHelp(
                        "Look at view",
                        "Look at the view all around you."
                    ),
                    true
                ) { game, _ ->
                    game.overworld.currentRegion?.visibleWithoutDiscovery = true
                    Reaction(
                        ReactionResult.OK,
                        "You look all around you, the view is excellent."
                    )
                }
            )
        }
    }
    internal companion object {
        internal const val NAME = "Tree House"
        private const val DESCRIPTION = "The tree house is a old wooden structure made from pallets " +
            "and other scrap wood that has been nailed to a large tree. From the treehouse you can see all around."
    }
}
