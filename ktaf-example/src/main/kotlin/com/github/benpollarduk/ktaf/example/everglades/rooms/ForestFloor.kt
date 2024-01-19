package com.github.benpollarduk.ktaf.example.everglades.rooms

import com.github.benpollarduk.ktaf.assets.locations.Direction
import com.github.benpollarduk.ktaf.assets.locations.Exit
import com.github.benpollarduk.ktaf.assets.locations.Room
import com.github.benpollarduk.ktaf.utilities.templates.AssetTemplate

internal class ForestFloor : AssetTemplate<Room> {
    override fun instantiate(): Room {
        return Room(
            NAME,
            DESCRIPTION,
            listOf(
                Exit(Direction.NORTH),
                Exit(Direction.SOUTH)
            )
        )
    }
    internal companion object {
        internal const val NAME = "Forest Floor"
        private const val DESCRIPTION = "The forest is dense, with a few patches of light breaking the darkness. " +
            "To the north is what looks like a small cave, to the south is the entrance to the forest."
    }
}
