package com.github.benpollarduk.ktaf.example.everglades.rooms

import com.github.benpollarduk.ktaf.assets.characters.PlayableCharacter
import com.github.benpollarduk.ktaf.assets.locations.Direction
import com.github.benpollarduk.ktaf.assets.locations.Exit
import com.github.benpollarduk.ktaf.assets.locations.Room
import com.github.benpollarduk.ktaf.utilities.templates.RoomTemplate

internal class ForestEntrance : RoomTemplate() {
    override fun instantiate(playableCharacter: PlayableCharacter): Room {
        return Room(
            NAME,
            DESCRIPTION,
            listOf(
                Exit(Direction.NORTH),
                Exit(Direction.UP)
            )
        )
    }
    internal companion object {
        internal const val NAME = "Forest Entrance"
        private const val DESCRIPTION = "You are standing on the edge of a beautiful forest. There is a parting " +
            "in the trees to the north and a tree house in one of the nearby trees."
    }
}
