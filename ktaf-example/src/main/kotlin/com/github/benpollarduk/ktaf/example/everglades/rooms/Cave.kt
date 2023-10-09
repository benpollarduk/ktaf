package com.github.benpollarduk.ktaf.example.everglades.rooms

import com.github.benpollarduk.ktaf.assets.characters.PlayableCharacter
import com.github.benpollarduk.ktaf.assets.locations.Direction
import com.github.benpollarduk.ktaf.assets.locations.Exit
import com.github.benpollarduk.ktaf.assets.locations.Room
import com.github.benpollarduk.ktaf.utilities.templates.RoomTemplate

internal class Cave : RoomTemplate() {
    override fun instantiate(playableCharacter: PlayableCharacter): Room {
        return Room(
            NAME,
            DESCRIPTION,
            listOf(
                Exit(Direction.SOUTH),
                Exit(Direction.EAST),
                Exit(Direction.DOWN),
            ),
        )
    }
    internal companion object {
        internal const val NAME = "Cave"
        private const val DESCRIPTION = "The cave is so dark you struggling to see, but you can make out a " +
            "faint glow coming from a crack in the rock leading below. A screeching noise is audible to the east."
    }
}
