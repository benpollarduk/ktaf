package com.github.benpollarduk.ktaf.example.everglades.rooms

import com.github.benpollarduk.ktaf.assets.locations.Direction
import com.github.benpollarduk.ktaf.assets.locations.Exit
import com.github.benpollarduk.ktaf.assets.locations.Room
import com.github.benpollarduk.ktaf.utilities.templates.AssetTemplate

internal class CaveMouth : AssetTemplate<Room> {
    override fun instantiate(): Room {
        return Room(
            NAME,
            DESCRIPTION,
            listOf(
                Exit(Direction.NORTH),
                Exit(Direction.SOUTH),
                Exit(Direction.WEST)
            )
        )
    }
    internal companion object {
        internal const val NAME = "Cave Mouth"
        private const val DESCRIPTION = "A cave mouth looms in front of you to the north. You can hear the sound " +
            "of the ocean coming from the west."
    }
}
